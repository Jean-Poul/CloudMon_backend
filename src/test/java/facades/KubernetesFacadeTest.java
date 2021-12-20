package facades;

import dto.DeploymentDTO;
import dto.DeploymentsDTO;
import dto.NamespaceDTO;
import dto.NamespacesDTO;
import dto.PodDTO;
import dto.PodsDTO;
import dto.ServiceDTO;
import dto.ServicesDTO;
import entities.Deployment;
import entities.Namespace;
import entities.Pod;
import entities.Service;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

public class KubernetesFacadeTest {

    private static EntityManagerFactory emf;
    private static KubernetesFacade facade;

    private Namespace namespace1, namespace2;
    private Service service1, service2;
    private Deployment deployment1, deployment2;
    private Pod pod1, pod2;

    public KubernetesFacadeTest() {

    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = KubernetesFacade.getKubernetesFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
        //      emf.close();
    }

// Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Namespace.deleteAllRows").executeUpdate();
            em.createNamedQuery("Service.deleteAllRows").executeUpdate();
            em.createNamedQuery("Deployment.deleteAllRows").executeUpdate();
            em.createNamedQuery("Pod.deleteAllRows").executeUpdate();

            namespace1 = new Namespace("kube-system", "active", "15h");
            namespace2 = new Namespace("ingress-nginx", "active", "2d17h");

            service1 = new Service("cert-manager", "cert-manager-webhook", "ClusterIP", "10.43.32.226", "<none>", "443/TCP", "15h", "app.kubernetes.io/component=webhook,app.kubernetes.io/instance=cert-manager,app.kubernetes.io/name=webhook");
            service2 = new Service("minio", "minio", "NodePort", "10.43.58.10", "<none>", "9000:32335/TCP,9001:32638/TCP", "2d23h", "app.kubernetes.io/instance=minio,app.kubernetes.io/name=minio");

            deployment1 = new Deployment("cert-manager", "cert-manager", "1/1", "1", "1", "15h", "cert-manager", "quay.io/jetstack/cert-manager-controller:v1.6.0", "app.kubernetes.io/component=controller,app.kubernetes.io/instance=cert-manager,app.kubernetes.io/name=cert-manager");
            deployment2 = new Deployment("kube-system", "snapshot-controller", "2/2", "2", "2", "4d14h", "snapshot-controller", "k8s.gcr.io/sig-storage/snapshot-controller:v4.2.1", "app=snapshot-controller");

            pod1 = new Pod("ingress-nginx", "nginx-ingress-controller-q225l", "1/1", "Running", "4", "66d", "10.33.74.13", "gc-rook-t001");
            pod2 = new Pod("kube-system", "rke-coredns-addon-deploy-job-hwsz2", "0/1", "Completed", "0", "66d", "10.33.74.13", "gc-rook-t001");

            em.persist(namespace1);
            em.persist(namespace2);

            em.persist(service1);
            em.persist(service2);

            em.persist(deployment1);
            em.persist(deployment2);

            em.persist(pod1);
            em.persist(pod2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    /**
     * Test of getKubernetesFacade method, of class KubernetesFacade.
     */
    @Test
    public void testGetKubernetesFacade() {
        System.out.println("getKubernetesFacade");

        assertEquals(KubernetesFacade.getKubernetesFacade(emf), KubernetesFacade.getKubernetesFacade(emf));
    }

    /**
     * Test of getAllNamespaces method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllNamespaces() throws Exception {
        System.out.println("getAllNamespaces");

        NamespacesDTO namesDTO = facade.getAllNamespaces();
        List<NamespaceDTO> namesList = namesDTO.getAll();

        assertThat(namesList, everyItem(Matchers.hasProperty("name")));
        assertThat(namesList, everyItem(Matchers.hasProperty("status")));
        assertThat(namesList, Matchers.hasItems(Matchers.<NamespaceDTO>hasProperty("age", is("15h")),
                Matchers.<NamespaceDTO>hasProperty("age", is("2d17h"))
        ));
    }

    /**
     * Test of getAllServices method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllServices() throws Exception {
        System.out.println("getAllServices");

        ServicesDTO servicesDTO = facade.getAllServices();
        List<ServiceDTO> serviceList = servicesDTO.getAll();

        assertThat(serviceList, everyItem(Matchers.hasProperty("namespace")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("name")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("type")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("clusterip")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("externalip")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("port")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("age")));
        assertThat(serviceList, everyItem(Matchers.hasProperty("selector")));

        assertThat(serviceList, Matchers.hasItems(Matchers.<ServiceDTO>hasProperty("type", is("ClusterIP")),
                Matchers.<ServiceDTO>hasProperty("type", is("NodePort"))
        ));
    }

    /**
     * Test of getAllDeployments method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllDeployments() throws Exception {
        System.out.println("getAllDeployments");

        DeploymentsDTO deploymentsDTO = facade.getAllDeployments();
        List<DeploymentDTO> deploymentList = deploymentsDTO.getAll();

        assertThat(deploymentList, everyItem(Matchers.hasProperty("namespace")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("name")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("ready")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("uptodate")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("available")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("age")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("containers")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("images")));
        assertThat(deploymentList, everyItem(Matchers.hasProperty("selector")));

        assertThat(deploymentList, Matchers.hasItems(Matchers.<DeploymentDTO>hasProperty("containers", is("cert-manager")),
                Matchers.<DeploymentDTO>hasProperty("containers", is("snapshot-controller"))
        ));
    }

    /**
     * Test of getAllPods method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllPods() throws Exception {
        System.out.println("getAllPods");

        PodsDTO podsDTO = facade.getAllPods();
        List<PodDTO> podList = podsDTO.getAll();

        assertThat(podList, everyItem(Matchers.hasProperty("namespace")));
        assertThat(podList, everyItem(Matchers.hasProperty("name")));
        assertThat(podList, everyItem(Matchers.hasProperty("ready")));
        assertThat(podList, everyItem(Matchers.hasProperty("status")));
        assertThat(podList, everyItem(Matchers.hasProperty("restarts")));
        assertThat(podList, everyItem(Matchers.hasProperty("age")));
        assertThat(podList, everyItem(Matchers.hasProperty("ip")));
        assertThat(podList, everyItem(Matchers.hasProperty("node")));

        assertThat(podList, Matchers.hasItems(Matchers.<PodDTO>hasProperty("status", is("Running")),
                Matchers.<PodDTO>hasProperty("status", is("Completed"))
        ));
    }
}

package rest;

import dto.DeploymentDTO;
import dto.NamespaceDTO;
import dto.PodDTO;
import dto.ServiceDTO;
import entities.Deployment;
import entities.Namespace;
import entities.Pod;
import entities.Service;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

// Uncomment the line below, to temporarily disable this test
// @Disabled
public class KubernetesResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private Namespace namespace1, namespace2;
    private Service service1, service2;
    private Deployment deployment1, deployment2;
    private Pod pod1, pod2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    // Start the test server
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    // Setup before any test has run
    @BeforeAll
    public static void setUpClass() {
        // This method must be called before requesting the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        // Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    @AfterAll
    public static void tearDownClass() {
        // Don't forget this if its counterpart was called in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the database in a known state before each test
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Delete existing namespaces, services, deployments and pods to get a fresh database
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

    // Remove any data after each test was run
    @AfterEach
    public void tearDown() {

    }

    /**
     * Test of getInfoForAll method, of class KubernetesResource.
     */
    @Test
    public void testGetInfoForAll() {
        System.out.println("getInfoForAll");

        given()
                .when()
                .get("/kubernetes")
                .then()
                .statusCode(200)
                .body(equalTo("{\"msg\":\"Hello from Kubernetes\"}"));

    }

    /**
     * Test of ping method, of class KubernetesResource.
     */
    @Test
    public void testPing() {
        System.out.println("Testing if /ping is up");

        given()
                .when()
                .get("/kubernetes/ping")
                .then()
                .statusCode(200)
                .body(equalTo("Service online"));
    }

    /**
     * Test of getNamespaces method, of class KubernetesResource.
     */
    @Test
    public void testGetNamespaces() throws Exception {
        System.out.println("getNamespaces");

        List<NamespaceDTO> nmsDTOs;

        nmsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/kubernetes/namespaces")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", NamespaceDTO.class);

        NamespaceDTO nm1DTO = new NamespaceDTO(namespace1);
        NamespaceDTO nm2DTO = new NamespaceDTO(namespace2);

        assertThat(nmsDTOs, containsInAnyOrder(nm1DTO, nm2DTO));

        assertThat(nmsDTOs, hasSize(2));
    }

    /**
     * Test of getServices method, of class KubernetesResource.
     */
    @Test
    public void testGetServices() throws Exception {
        System.out.println("getServices");

        List<ServiceDTO> srvsDTOs;

        srvsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/kubernetes/services")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", ServiceDTO.class);

        ServiceDTO srv1DTO = new ServiceDTO(service1);
        ServiceDTO srv2DTO = new ServiceDTO(service2);

        assertThat(srvsDTOs, containsInAnyOrder(srv1DTO, srv2DTO));

        assertThat(srvsDTOs, hasSize(2));
    }

    /**
     * Test of getDeployments method, of class KubernetesResource.
     */
    @Test
    public void testGetDeployments() throws Exception {
        System.out.println("getDeployments");

        List<DeploymentDTO> dploysDTOs;

        dploysDTOs = given()
                .contentType("application/json")
                .when()
                .get("/kubernetes/deployments")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", DeploymentDTO.class);

        DeploymentDTO dply1DTO = new DeploymentDTO(deployment1);
        DeploymentDTO dply2DTO = new DeploymentDTO(deployment2);

        assertThat(dploysDTOs, containsInAnyOrder(dply1DTO, dply2DTO));

        assertThat(dploysDTOs, hasSize(2));
    }

    /**
     * Test of getPods method, of class KubernetesResource.
     */
    @Test
    public void testGetPods() throws Exception {
        System.out.println("getPods");

        List<PodDTO> podsDTOs;

        podsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/kubernetes/pods")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", PodDTO.class);

        PodDTO pod1DTO = new PodDTO(pod1);
        PodDTO pod2DTO = new PodDTO(pod2);

        assertThat(podsDTOs, containsInAnyOrder(pod1DTO, pod2DTO));

        assertThat(podsDTOs, hasSize(2));
    }
}

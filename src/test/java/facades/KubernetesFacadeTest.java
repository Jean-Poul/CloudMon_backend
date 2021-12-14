package facades;

import dto.DeploymentsDTO;
import dto.NamespaceDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import entities.Namespace;
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

            namespace1 = new Namespace("kube-system", "active", "15h");
            namespace2 = new Namespace("ingress-nginx", "active", "2d17h");

            em.persist(namespace1);
            em.persist(namespace2);

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
        assertThat(namesList, Matchers.hasItems(Matchers.<NamespaceDTO>hasProperty("age", is("2d17h")),
                Matchers.<NamespaceDTO>hasProperty("age", is("15h"))
        ));
    }

//    /**
//     * Test of getAllServices method, of class KubernetesFacade.
//     */
//    @Test
//    public void testGetAllServices() throws Exception {
//        System.out.println("getAllServices");
//        KubernetesFacade instance = new KubernetesFacade();
//        ServicesDTO expResult = null;
//        ServicesDTO result = instance.getAllServices();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllDeployments method, of class KubernetesFacade.
//     */
//    @Test
//    public void testGetAllDeployments() throws Exception {
//        System.out.println("getAllDeployments");
//        KubernetesFacade instance = new KubernetesFacade();
//        DeploymentsDTO expResult = null;
//        DeploymentsDTO result = instance.getAllDeployments();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllPods method, of class KubernetesFacade.
//     */
//    @Test
//    public void testGetAllPods() throws Exception {
//        System.out.println("getAllPods");
//        KubernetesFacade instance = new KubernetesFacade();
//        PodsDTO expResult = null;
//        PodsDTO result = instance.getAllPods();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}

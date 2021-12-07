
package facades;

import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author jplm
 */
@Disabled
public class KubernetesFacadeTest {
    
    public KubernetesFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getKubernetesFacade method, of class KubernetesFacade.
     */
    @Test
    public void testGetKubernetesFacade() {
        System.out.println("getKubernetesFacade");
        EntityManagerFactory _emf = null;
        KubernetesFacade expResult = null;
        KubernetesFacade result = KubernetesFacade.getKubernetesFacade(_emf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllNamespaces method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllNamespaces() throws Exception {
        System.out.println("getAllNamespaces");
        KubernetesFacade instance = new KubernetesFacade();
        NamespacesDTO expResult = null;
        NamespacesDTO result = instance.getAllNamespaces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllServices method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllServices() throws Exception {
        System.out.println("getAllServices");
        KubernetesFacade instance = new KubernetesFacade();
        ServicesDTO expResult = null;
        ServicesDTO result = instance.getAllServices();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllDeployments method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllDeployments() throws Exception {
        System.out.println("getAllDeployments");
        KubernetesFacade instance = new KubernetesFacade();
        DeploymentsDTO expResult = null;
        DeploymentsDTO result = instance.getAllDeployments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPods method, of class KubernetesFacade.
     */
    @Test
    public void testGetAllPods() throws Exception {
        System.out.println("getAllPods");
        KubernetesFacade instance = new KubernetesFacade();
        PodsDTO expResult = null;
        PodsDTO result = instance.getAllPods();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

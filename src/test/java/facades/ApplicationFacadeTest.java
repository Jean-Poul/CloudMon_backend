//
//package facades;
//
//import dto.ApplicationDTO;
//import dto.ApplicationsDTO;
//import javax.persistence.EntityManagerFactory;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class ApplicationFacadeTest {
//    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }
//
//    /**
//     * Test of getApplicationFacade method, of class ApplicationFacade.
//     */
//    @Test
//    public void testGetApplicationFacade() {
//        System.out.println("getApplicationFacade");
//        EntityManagerFactory _emf = null;
//        ApplicationFacade expResult = null;
//        ApplicationFacade result = ApplicationFacade.getApplicationFacade(_emf);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllApplications method, of class ApplicationFacade.
//     */
//    @Test
//    public void testGetAllApplications() throws Exception {
//        System.out.println("getAllApplications");
//        ApplicationFacade instance = new ApplicationFacade();
//        ApplicationsDTO expResult = null;
//        ApplicationsDTO result = instance.getAllApplications();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addApplication method, of class ApplicationFacade.
//     */
//    @Test
//    public void testAddApplication() throws Exception {
//        System.out.println("addApplication");
//        String name = "";
//        String version = "";
//        String location = "";
//        ApplicationFacade instance = new ApplicationFacade();
//        ApplicationDTO expResult = null;
//        ApplicationDTO result = instance.addApplication(name, version, location);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteApplication method, of class ApplicationFacade.
//     */
//    @Test
//    public void testDeleteApplication() throws Exception {
//        System.out.println("deleteApplication");
//        long id = 0L;
//        ApplicationFacade instance = new ApplicationFacade();
//        ApplicationDTO expResult = null;
//        ApplicationDTO result = instance.deleteApplication(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateApplication method, of class ApplicationFacade.
//     */
//    @Test
//    public void testUpdateApplication() throws Exception {
//        System.out.println("updateApplication");
//        ApplicationDTO a = null;
//        ApplicationFacade instance = new ApplicationFacade();
//        ApplicationDTO expResult = null;
//        ApplicationDTO result = instance.updateApplication(a);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
//}

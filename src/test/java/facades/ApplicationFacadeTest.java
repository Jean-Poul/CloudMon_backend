package facades;

import dto.ApplicationDTO;
import dto.ApplicationsDTO;
import entities.Application;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

// Uncomment the line below, to temporarily disable this test
// @Disabled
public class ApplicationFacadeTest {

    private static EntityManagerFactory emf;
    private static ApplicationFacade facade;

    private Application app1;
    private Application app2;

    public ApplicationFacadeTest() {
    }

    // Setup before any test has run
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ApplicationFacade.getApplicationFacade(emf);
    }

    // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    @AfterAll
    public static void tearDownClass() {
        //emf.close();
    }

    // Setup the database in a known state before each test
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Application.deleteAllRows").executeUpdate();

            app1 = new Application("Kubernetes", "1.9a", "Cluster-009");
            app2 = new Application("GitLab", "1.12c", "Cluster-012");

            em.persist(app1);
            em.persist(app2);

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
     * Test of getApplicationFacade method, of class ApplicationFacade.
     */
    @Test
    public void testGetApplicationFacade() {
        System.out.println("Get ApplicationFacade has private access modifiers, so encapsulation works and cannot perform test");

        assertEquals(ApplicationFacade.getApplicationFacade(emf), ApplicationFacade.getApplicationFacade(emf));
    }

    /**
     * Test of getApplicationCount method, of class ApplicationFacade.
     */
    @Test
    public void testGetApplicationCount() throws NoConnectionException {
        System.out.println("Get application count");

        try {
            assertEquals(2, facade.getApplicationCount(), "Expects two rows in the database");
        } catch (NoConnectionException e) {
            throw new NoConnectionException("No connection to the database");
        }

    }

    /**
     * Test of getAllApplications method, of class ApplicationFacade.
     */
    @Test
    public void testGetAllApplications() throws Exception {
        System.out.println("Get all applications");

        ApplicationsDTO appsDTO = facade.getAllApplications();
        List<ApplicationDTO> appList = appsDTO.getAll();

        assertThat(appList, everyItem(Matchers.hasProperty("name")));
        assertThat(appList, everyItem(Matchers.hasProperty("version")));
        assertThat(appList, Matchers.hasItems(Matchers.<ApplicationDTO>hasProperty("location", is("Cluster-009")),
                Matchers.<ApplicationDTO>hasProperty("location", is("Cluster-012"))
        ));
    }

    /**
     * Test of getApplication method, of class ApplicationFacade.
     */
    @Test
    public void testgetApplication() throws NotFoundException {
        System.out.println("Get Application");

        ApplicationDTO appDTO = facade.getApplication(app1.getId());

        assertEquals("Kubernetes", appDTO.getName());
        assertEquals("1.9a", appDTO.getVersion());
        assertEquals("Cluster-009", appDTO.getLocation());
    }

    /**
     * Test of addApplication method, of class ApplicationFacade.
     */
    @Test
    public void testAddApplication() throws Exception {
        System.out.println("Add application");

        String appName = "Travis";
        String version = "2.7";
        String location = "Cluster-test-003";

        Application app = new Application(appName, version, location);

        facade.addApplication(app.getName(), app.getVersion(), app.getLocation());

        ApplicationsDTO appsDTO = facade.getAllApplications();
        long id = appsDTO.getAll().get(0).getId();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Application appl = em.find(Application.class, id);

            em.getTransaction().commit();

            assertEquals(appl, em.find(Application.class, id));
            assertEquals(3, facade.getApplicationCount(), "Expects three rows in the database");
        } catch (NoConnectionException e) {
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
    }

    /**
     * Test of deleteApplication method, of class ApplicationFacade.
     */
    @Test
    public void testDeleteApplication() throws Exception {
        System.out.println("Delete application");

        ApplicationDTO appDTO = new ApplicationDTO(app1);
        facade.deleteApplication(app1.getId());
        ApplicationDTO appsDTO = new ApplicationDTO(app2);

        assertThat(appDTO.getId(), is(not(appsDTO.getId())));
        assertEquals(1, facade.getApplicationCount());

        facade.deleteApplication(app2.getId());

        assertEquals(0, facade.getApplicationCount());
    }

    /**
     * Test of updateApplication method, of class ApplicationFacade.
     */
    @Test
    public void testUpdateApplication() throws Exception {
        System.out.println("Update application");

        String newName = "testName";
        String newVersion = "v1.12";
        String newLocation = "Cluster-Test-012";

        ApplicationDTO updateApp = new ApplicationDTO(app1);

        assertEquals("Kubernetes", updateApp.getName());
        assertEquals("1.9a", updateApp.getVersion());
        assertEquals("Cluster-009", updateApp.getLocation());

        updateApp.setName(newName);
        updateApp.setVersion(newVersion);
        updateApp.setLocation(newLocation);

        updateApp = facade.updateApplication(updateApp);

        updateApp = facade.getApplication(updateApp.getId());

        assertEquals("testName", updateApp.getName());
        assertEquals("v1.12", updateApp.getVersion());
        assertEquals("Cluster-Test-012", updateApp.getLocation());
    }

}

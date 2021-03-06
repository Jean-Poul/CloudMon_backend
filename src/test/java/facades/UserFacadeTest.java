package facades;

import dto.UserDTO;
import dto.UsersDTO;
import entities.Role;
import utils.EMF_Creator;
import entities.User;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import security.errorhandling.AuthenticationException;

// Uncomment the line below, to temporarily disable this test
// @Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private User u1, u2;
    private Role r1, r2;

    public UserFacadeTest() {
    }

    // Setup before any test has run
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
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
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();

            r1 = new Role("user");
            r2 = new Role("admin");
            u1 = new User("testuser1", "testpass1");
            u2 = new User("testuser2", "testpass2");

            u1.addRole(r1);
            u2.addRole(r2);

            em.persist(r1);
            em.persist(r2);
            em.persist(u1);
            em.persist(u2);

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
     * Test of getUserFacade method, of class UserFacade.
     */
    @Test
    public void testGetUserFacade() {
        System.out.println("Get UserFacade has private access modifiers, so encapsulation works and cannot perform test");

        assertEquals(UserFacade.getUserFacade(emf), UserFacade.getUserFacade(emf));
    }

    /**
     * Test of getUserCount method, of class UserFacade.
     */
    @Test
    public void testUserCount() throws NoConnectionException {
        System.out.println("Get user count");

        try {
            assertEquals(2, facade.getUserCount(), "Expects two rows in the database");
        } catch (NoConnectionException e) {
            throw new NoConnectionException("No connection to the database");
        }

    }

    /**
     * Test of getVeryfiedUser method, of class UserFacade.
     */
    @Test
    public void testGetVeryfiedUser() throws AuthenticationException {
        System.out.println("Get veryfied user");

        String pass = u1.getUserPass();

        assertEquals(u1.getUserName(), "testuser1");
        assertEquals(u1.getUserPass(), pass);
        assertThat(u1.getUserName(), is(not("Batman")));
        assertThat(u1.getUserPass(), is(not("Robin")));
    }

    /**
     * Test to make sure an unwanted person can't get information without proper authentication
     */
    @Test
    public void testGetRoleList() {
        System.out.println("Get role list");

        assertEquals(u1.getRolesAsStrings().get(0), r1.getRoleName());
        assertEquals(u2.getRolesAsStrings().get(0), r2.getRoleName());
    }

    /**
     * Test of addUser method, of class UserFacade.
     */
    @Test
    public void testAddUser() throws Exception, AuthenticationException, NoConnectionException {
        System.out.println("Add user");

        String userName = "ping";
        String pass = "pong";

        User usr = new User(userName, pass);

        EntityManager em = emf.createEntityManager();
        try {
            facade.addUser(usr.getUserName(), usr.getUserPass());
            em.getTransaction().begin();

            User us = em.find(User.class, userName);

            em.getTransaction().commit();

            assertEquals(us, em.find(User.class, userName));
            assertEquals(3, facade.getUserCount(), "Expects three rows in the database");
        } catch (NoConnectionException | AuthenticationException e) {
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
    }

    /**
     * Test of getAllUsers method, of class UserFacade.
     */
    @Test
    public void testGetAllUsers() throws NoConnectionException {
        System.out.println("Get All users");

        UsersDTO usersDTO = facade.getAllUsers();
        List<UserDTO> list = usersDTO.getAll();

        assertThat(list, everyItem(Matchers.hasProperty("password")));
        assertThat(list, everyItem(Matchers.hasProperty("lastLoginTime")));
        assertThat(list, Matchers.hasItems(Matchers.<UserDTO>hasProperty("userID", is("testuser1")),
                Matchers.<UserDTO>hasProperty("userID", is("testuser2"))
        ));
    }

    /**
     * Test of getUser method, of class UserFacade.
     */
    @Test
    public void testGetUser() throws NoConnectionException, NotFoundException {
        System.out.println("Get User");

        UserDTO userDTO = facade.getUser(u1.getUserName());

        assertEquals("testuser1", userDTO.getUserID());
        assertEquals("user", u1.getRolesAsStrings().get(0));
    }

}

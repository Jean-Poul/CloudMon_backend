package facades;

import dto.UserDTO;
import dto.UsersDTO;
import entities.Role;
import utils.EMF_Creator;
import entities.User;
import errorhandling.NoConnectionException;
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
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private User u1, u2;
    private Role r1, r2;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testUserCount() {
        assertEquals(2, facade.getUserCount(), "Expects two rows in the database");
    }

    @Test
    public void testGetVeryfiedUser() throws AuthenticationException {
        String pass = u1.getUserPass();

        assertEquals(u1.getUserName(), "testuser1");
        assertEquals(u1.getUserPass(), pass);
        assertThat(u1.getUserName(), is(not("Batman")));
        assertThat(u1.getUserPass(), is(not("Robin")));
    }

    @Test
    public void testGetRoleList() {
        assertEquals(u1.getRolesAsStrings().get(0), r1.getRoleName());
        assertEquals(u2.getRolesAsStrings().get(0), r2.getRoleName());
    }

    @Test
    public void testAddUser() throws Exception {
        String userName = "ping";
        String pass = "pong";

        User uls = new User(userName, pass);
        UserDTO u = new UserDTO(uls);

        System.out.println(u);

        facade.addUser(userName, pass);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            User us = em.find(User.class, userName);
            System.out.println(us.getUserName());

            em.getTransaction().commit();

            assertEquals(us, em.find(User.class, userName));
            assertEquals(3, facade.getUserCount(), "Expects three rows in the database");
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllUsers() throws NoConnectionException {
        UsersDTO usersDTO = facade.getAllUsers();
        List<UserDTO> list = usersDTO.getAll();

        System.out.println("Liste af users: " + list);

        assertThat(list, everyItem(Matchers.hasProperty("password")));
        assertThat(list, everyItem(Matchers.hasProperty("lastLoginTime")));
        assertThat(list, Matchers.hasItems(Matchers.<UserDTO>hasProperty("userID", is("testuser1")),
                Matchers.<UserDTO>hasProperty("userID", is("testuser2"))
        ));
    }

}

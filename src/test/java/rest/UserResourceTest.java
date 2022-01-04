package rest;

import dto.UserDTO;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
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


public class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private User u1, u2, u3;
    private Role r1, r2;

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
    public static void closeTestServer() {
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
            // Delete existing users and roles to get a clean database
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();

            r1 = new Role("user");
            r2 = new Role("admin");

            u1 = new User("testuser1", "testpass1");
            u2 = new User("testuser2", "testpass2");
            u3 = new User("Test", "Me");

            u3.addRole(r1);
            u1.addRole(r1);
            u2.addRole(r2);

            em.persist(r1);
            em.persist(r2);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);

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
     * Test of endpoint /users, of class UserResource.
     */
    @Test
    public void testServerIsUp() {
        System.out.println("Testing if server up");

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    /**
     * Test of Ping method, of class UserResource.
     */
    @Test
    public void testPing() {
        System.out.println("Testing if /ping is up");

        given()
                .when()
                .get("/users/ping")
                .then()
                .statusCode(200)
                .body(equalTo("Service online"));
    }

    /**
     * Test of Count method, of class UserResource.
     */
    @Test
    public void testCount() throws Exception {
        System.out.println("Count users");

        given()
                .contentType("application/json")
                .get("/users/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }

    /**
     * Test to make sure an unwanted person can't get information without proper authentication
     */
    @Test
    public void testGetRole() throws Exception {
        // Can not get a role without being logged in.
        // We also test login with Postman
        System.out.println("Get null user role info when not logged in");

        given()
                .when()
                .get("users/" + "user")
                .then().statusCode(403)
                .body("roleName", equalTo(null));
    }

    /**
     * Test of getInfoForAll method, of class UserResource.
     */
    @Test
    public void testGetInfoForAll() {
        System.out.println("Get info for all");
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("msg", equalTo("Hello from users"));
    }

    /**
     * Test of allUsers method, of class UserResource.
     */
    @Test
    public void testAllUsers() throws Exception {
        System.out.println("All users");

        List<UserDTO> usersDTOs;

        usersDTOs = given()
                .contentType("application/json")
                .when()
                .get("/users/all")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", UserDTO.class);

        UserDTO u1DTO = new UserDTO(u1);
        UserDTO u2DTO = new UserDTO(u2);
        UserDTO u3DTO = new UserDTO(u3);

        assertThat(usersDTOs, containsInAnyOrder(u1DTO, u2DTO, u3DTO));

        assertThat(usersDTOs, hasSize(3));
    }

    // This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    /**
     * Test of getFromUser method, of class UserResource.
     */
    @Test
    public void testGetFromUser() {
        System.out.println("Get from user");

        String json = String.format("{username: \"%s\", password: \"%s\"}", "testuser1", "testpass1");

        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/users/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hej testuser1 du er ved at logge ud."));
    }

    /**
     * Test of getFromAdmin method, of class UserResource.
     */
    @Test
    public void testGetFromAdmin() {
        System.out.println("Get from admin");

        String json = String.format("{username: \"%s\", password: \"%s\"}", "testuser2", "testpass2");

        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/users/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hej testuser2 du er ved at logge ud."));
    }

    /**
     * Test of addUser method, of class UserResource.
     */
    @Test
    public void testAddUser() {
        System.out.println("Add user");

        User testUser = new User("Batman", "Robin");
        UserDTO testDTO = new UserDTO(testUser);

        given()
                .contentType("application/json")
                .body(testDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userID", equalTo("Batman"));
    }

    /**
     * Test of getUser method, of class UserResource.
     */
    @Test
    public void testGetUser() {
        System.out.println("Get user");

        UserDTO usr = new UserDTO(u1);
        String expected = usr.getUserID();

        given()
                .contentType("application/json")
                .when()
                .get("users/username/" + u1.getUserName())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("userID", equalTo(expected));
    }

}

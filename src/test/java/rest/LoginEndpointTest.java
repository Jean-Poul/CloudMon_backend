package rest;

import entities.User;
import entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

// Uncomment the line below, to temporarily disable this test
// @Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

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
            // Delete existing users and roles to get a fresh database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "popcorn");
            user.addRole(userRole);
            User admin = new User("admin", "popcorn");
            admin.addRole(adminRole);
            User both = new User("user_admin", "popcorn");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Remove any data after each test was run
    @AfterEach
    public void tearDown() {

    }

    // This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    // Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);

        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut() {
        securityToken = null;
    }

    /**
     * Test of endpoint /users, of class UserResource.
     */
    @Test
    public void serverIsRunning() {
        System.out.println("Testing if server up");

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    /**
     * Test of endpoint /users, of class UserResource.
     */
    @Test
    public void testRestNoAuthenticationRequired() {
        System.out.println("testRestNoAuthenticationRequired");

        given()
                .contentType("application/json")
                .when()
                .get("/users/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello from users"));
    }

    /**
     * Test of endpoint /users/admin, of class UserResource.
     */
    @Test
    public void testRestForAdmin() {
        System.out.println("testRestForAdmin");

        login("admin", "popcorn");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/users/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hej admin du er ved at logge ud."));
    }

    /**
     * Test of endpoint /users/user, of class UserResource.
     */
    @Test
    public void testRestForUser() {
        System.out.println("testRestForUser");

        login("user", "popcorn");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/users/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hej user du er ved at logge ud."));
    }

    /**
     * Test of endpoint /users/admin, of class UserResource.
     */
    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        System.out.println("testAutorizedUserCannotAccesAdminPage");

        login("user", "popcorn");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/users/admin").then() // Call Admin endpoint as user
                .statusCode(401);
    }

    /**
     * Test of endpoint /users/user, of class UserResource.
     */
    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        System.out.println("testAutorizedAdminCannotAccesUserPage");

        login("admin", "popcorn");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/users/user").then() // Call User endpoint as Admin
                .statusCode(401);
    }

    /**
     * Test of endpoint /users/admin, of class UserResource.
     */
    @Test
    public void testRestForMultiRole1() {
        System.out.println("testRestForMultiRole1");

        login("user_admin", "popcorn");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/users/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hej user_admin du er ved at logge ud."));
    }

    /**
     * Test of endpoint /users/user, of class UserResource.
     */
    @Test
    public void testRestForMultiRole2() {
        System.out.println("testRestForMultiRole2");

        login("user_admin", "popcorn");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/users/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hej user_admin du er ved at logge ud."));
    }

    /**
     * Test of endpoint /users/user, of class UserResource.
     */
    @Test
    public void userNotAuthenticated() {
        System.out.println("userNotAuthenticated");

        logOut();

        given()
                .contentType("application/json")
                .when()
                .get("/users/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    /**
     * Test of endpoint /users/admin, of class UserResource.
     */
    @Test
    public void adminNotAuthenticated() {
        System.out.println("adminNotAuthenticated");

        logOut();

        given()
                .contentType("application/json")
                .when()
                .get("/users/admin").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

}

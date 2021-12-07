package rest;

import dto.UserDTO;
import entities.Role;
import entities.User;
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
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

public class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private User u1, u2, u3;
    private Role r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
//This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
//Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

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

    @AfterEach
    public void tearDown() {
    }

    //@Disabled
    @Test
    public void testServerIsUp() {

        System.out.println("Testing if server up");

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200);

    }

    //@Disabled
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

    //@Disabled
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

    /**
     * Test of getFromUser method, of class UserResource.
     */
//    @Test
//    public void testGetFromUser() {
//        System.out.println("Get from user");
//        UserResource instance = new UserResource();
//        String expResult = "";
//        String result = instance.getFromUser();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFromAdmin method, of class UserResource.
//     */
//    @Test
//    public void testGetFromAdmin() {
//        System.out.println("getFromAdmin");
//        UserResource instance = new UserResource();
//        String expResult = "";
//        String result = instance.getFromAdmin();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//

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

}

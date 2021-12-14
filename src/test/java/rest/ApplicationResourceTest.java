package rest;

import dto.ApplicationDTO;
import entities.Application;
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
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

public class ApplicationResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private Application app1, app2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

//    public ApplicationResourceTest() {
//    }

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
    public static void tearDownClass() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Application.deleteAllRows").executeUpdate();

            app1 = new Application("Harbor", "5.2c", "Cluster-prod-112");
            app2 = new Application("Travis", "12.9", "Cluster-test-009");

            em.persist(app1);
            em.persist(app2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of endpoint /apps, of class ApplicationResource.
     */
    //@Disabled
    @Test
    public void testServerIsUp() {
        System.out.println("Testing if server up");

        given()
                .when()
                .get("/apps")
                .then()
                .statusCode(200);
    }

    /**
     * Test of getInfoForAll method, of class ApplicationResource.
     */
    @Test
    public void testGetInfoForAll() {
        System.out.println("Get info for all");

        given()
                .when()
                .get("/apps")
                .then()
                .statusCode(200)
                .body(equalTo("{\"msg\":\"Hello from application\"}"));
    }

    /**
     * Test of ping method, of class ApplicationResource.
     */
    @Test
    public void testPing() {
        System.out.println("Testing if /ping is up");

        given()
                .when()
                .get("/apps/ping")
                .then()
                .statusCode(200)
                .body(equalTo("Service online"));
    }

    /**
     * Test of getUserCount method, of class ApplicationResource.
     */
    @Test
    public void testGetApplicationCount() throws Exception {
        System.out.println("Count applications");

        given()
                .contentType("application/json")
                .get("/apps/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }

    /**
     * Test of getAllApplications method, of class ApplicationResource.
     */
    @Test
    public void testGetAllApplications() throws Exception {
        System.out.println("All applications");

        List<ApplicationDTO> appsDTOs;

        appsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/apps/all")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().jsonPath().getList("all", ApplicationDTO.class);

        ApplicationDTO app1DTO = new ApplicationDTO(app1);
        ApplicationDTO app2DTO = new ApplicationDTO(app2);

        assertThat(appsDTOs, containsInAnyOrder(app1DTO, app2DTO));

        assertThat(appsDTOs, hasSize(2));
    }

    /**
     * Test of addApplication method, of class ApplicationResource.
     */
    @Test
    public void testAddApplication() throws Exception {
        System.out.println("Add application");

        Application addApp = new Application("Rancher", "2.0", "Cluster-prod-001");
        ApplicationDTO addDTO = new ApplicationDTO(addApp);

        given()
                .contentType("application/json")
                .body(addDTO)
                .when()
                .post("/apps")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Rancher"))
                .body("version", equalTo("2.0"))
                .body("location", equalTo("Cluster-prod-001"));
    }

    /**
     * Test of deleteApplication method, of class ApplicationResource.
     */
    @Test
    public void testDeleteApplication() throws Exception {
        System.out.println("Delete application");

        ApplicationDTO delDTO = new ApplicationDTO(app2);

        String name = delDTO.getName();
        String version = delDTO.getVersion();
        String location = delDTO.getLocation();

        given()
                .contentType("application/json")
                //      .body(delDTO)
                .when()
                .delete("/apps/delete/" + delDTO.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("name", equalTo(name))
                .body("version", equalTo(version))
                .body("location", equalTo(location));
    }

    /**
     * Test of getApplication method, of class ApplicationResource.
     */
    @Test
    public void testGetApplication() throws Exception {
        System.out.println("Get application");

        ApplicationDTO app = new ApplicationDTO(app1);

        long id = app.getId();

        String name = app.getName();
        String version = app.getVersion();
        String location = app.getLocation();

        given()
                .contentType("application/json")
                .when()
                .get("apps/" + id)
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("id", notNullValue())
                .body("name", equalTo(name))
                .body("version", equalTo(version))
                .body("location", equalTo(location));
    }

    /**
     * Test of updateApplication method, of class ApplicationResource.
     */
    @Test
    public void testUpdateApplication() throws Exception {
        System.out.println("Update application");

        ApplicationDTO delDTO = new ApplicationDTO(app2);

        String newName = "GitHub";
        String newVersion = "0.1";
        String newLocation = "Cluster-prod-002";

        delDTO.setName(newName);
        delDTO.setVersion(newVersion);
        delDTO.setLocation(newLocation);;

        given()
                .contentType("application/json")
                .body(delDTO)
                .when()
                .put("/apps/update/" + delDTO.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("id", notNullValue())
                .body("name", equalTo(newName))
                .body("version", equalTo(newVersion))
                .body("location", equalTo(newLocation));

    }

}

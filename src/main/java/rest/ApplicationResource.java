package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ApplicationDTO;
import dto.ApplicationsDTO;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import facades.ApplicationFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 */
@Path("apps")
public class ApplicationResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ApplicationFacade FACADE = ApplicationFacade.getApplicationFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * GET *
     */
    // To verify if there is a connection to the endpoint apps
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from application\"}";
    }

    // Ping reponse
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getApplicationCount() throws NoConnectionException {
        long count = FACADE.getApplicationCount();
        return "{\"count\":" + count + "}";  // Done manually so no need for a DTO
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String getAllApplications() throws NoConnectionException {
        ApplicationsDTO apps = FACADE.getAllApplications();
        return GSON.toJson(apps);
    }

    @Path("/{appId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getApplication(@PathParam("appId") long appId) throws NotFoundException {
        return GSON.toJson(FACADE.getApplication(appId));
    }

    /**
     * POST *
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addApplication(String a) throws Exception {
        ApplicationDTO app = GSON.fromJson(a, ApplicationDTO.class);
        ApplicationDTO addApp = FACADE.addApplication(app.getName(), app.getVersion(), app.getLocation());
        return GSON.toJson(addApp);
    }

    /**
     * DELETE *
     */
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/delete/{id}")
    public String deleteApplication(@PathParam("id") long id) throws NotFoundException {
        ApplicationDTO appDelete = FACADE.deleteApplication(id);
        return GSON.toJson(appDelete);
    }

    /**
     * UPDATE *
     */
    @PUT
    @Path("/update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateApplication(@PathParam("id") long id, String name) throws NotFoundException {
        ApplicationDTO appDTO = GSON.fromJson(name, ApplicationDTO.class);
        appDTO.setId(id);
        ApplicationDTO appNew = FACADE.updateApplication(appDTO);
        return GSON.toJson(appNew);
    }

}

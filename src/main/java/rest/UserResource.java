package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.UserDTO;
import dto.UsersDTO;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 */
@Path("users")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // To verify if there is a connection to the endpoint users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from users\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUserCount() throws NoConnectionException {

        long count = FACADE.getUserCount();

        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO

    }

    // Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String allUsers() throws NoConnectionException {
        UsersDTO users = FACADE.getAllUsers();
        return GSON.toJson(users);
//        EntityManager em = EMF.createEntityManager();
//        try {
//            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
//            List<User> users = query.getResultList();
//            return "[" + users.size() + "]";
//        } finally {
//            em.close();
//        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hej " + thisuser + " du er ved at logge ud.\"}";
        //return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hej " + thisuser + " du er ved at logge ud.\"}";
        //return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addUser(String user) throws Exception {
        UserDTO u = GSON.fromJson(user, UserDTO.class);
        UserDTO addUser = FACADE.addUser(u.getUserID(), u.getPassword());
        return GSON.toJson(addUser);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/username/{name}")
    public String getUser(@PathParam("name") String name) throws NoConnectionException, NotFoundException {
        return GSON.toJson(FACADE.getUser(name));
    }

}

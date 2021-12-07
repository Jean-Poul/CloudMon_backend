/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author jplm
 */
@Path("apps")
public class ApplicationResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ApplicationFacade FACADE = ApplicationFacade.getApplicationFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ApplicationResource
     */
//    public ApplicationResource() {
//    }
// slettes højst sandsynligt
    /**
     * GET *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("test")
    public String test() {
        return "Hello from application";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllApplications() throws NoConnectionException {
        ApplicationsDTO apps = FACADE.getAllApplications();
        return GSON.toJson(apps);
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
    @Path("delete/{id}")
    public String deleteApplication(@PathParam("id") long id) throws NotFoundException {

        ApplicationDTO appDelete = FACADE.deleteApplication(id);
        return GSON.toJson(appDelete);

    }

    /**
     * UPDATE *
     */
//    @PUT
//    @Path("update")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response updatePerson(String person) throws NotFoundException {
//        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
//        FACADE.updatePerson(personDTO);
//        return Response.status(Response.Status.OK).entity("Person updated OK").build();
//    }
//    @PUT
//    @Path("update")
////@Path("update/{name}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
////public String updateApplication(@PathParam("name") String name, String app) throws NotFoundException {
//    public String updateApplication(String app) throws NotFoundException {
//        //   System.out.println("NAME: " + name);
//        System.out.println("APP: " + app);
//        ApplicationDTO appDTO = GSON.fromJson(app, ApplicationDTO.class);
//        //  appDTO.setName(name);
//        ApplicationDTO newApp = FACADE.updateApplication(appDTO);
//        return GSON.toJson(newApp);
//    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////   
    
    @Path("{appId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getApp(@PathParam("appId") long appId) throws NotFoundException {
        return GSON.toJson(FACADE.getApp(appId));
    }
    
    @PUT
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updateApp(@PathParam("id") long id, String name) throws NotFoundException {
        ApplicationDTO appDTO = GSON.fromJson(name, ApplicationDTO.class);
        appDTO.setId(id);
        ApplicationDTO appNew = FACADE.updateApplication(appDTO);
        return GSON.toJson(appNew);
    }

}

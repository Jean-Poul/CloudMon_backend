package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import errorhandling.NoConnectionException;
import facades.KubernetesFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 */
@Path("kubernetes")
public class KubernetesResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final KubernetesFacade FACADE = KubernetesFacade.getKubernetesFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * GET *
     */
    // To verify if there is a connection to the endpoint kubernetes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from Kubernetes\"}";
    }

    // Ping reponse
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/namespaces")
    public String getNamespaces() throws IOException, NoConnectionException {
        NamespacesDTO nsDTO = FACADE.getAllNamespaces();
        return GSON.toJson(nsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/services")
    public String getServices() throws IOException, NoConnectionException {
        ServicesDTO servicesDTO = FACADE.getAllServices();
        return GSON.toJson(servicesDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deployments")
    public String getDeployments() throws IOException, NoConnectionException {
        DeploymentsDTO deploymentsDTO = FACADE.getAllDeployments();
        return GSON.toJson(deploymentsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pods")
    public String getPods() throws IOException, NoConnectionException {
        PodsDTO podsDTO = FACADE.getAllPods();
        return GSON.toJson(podsDTO);
    }

}

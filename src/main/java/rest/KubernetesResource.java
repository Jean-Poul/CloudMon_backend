package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import entities.Deployment;
import entities.Namespace;
import entities.Pod;
import entities.Service;
import errorhandling.NoConnectionException;
import facades.KubernetesFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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

//  Undersøg/google nærmere omkring UriInfo - tror det kan slettes
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of KubernetesResource
     */
//    public KubernetesResource() {
//    }
    /**
     * GET *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from Kubernetes\"}";
    }

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

//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = EMF.createEntityManager();
//
//        Namespace ns;
//        NamespacesDTO nsDTO = null;
//
//        try {
//            nsDTO = FACADE.getAllNamespaces();
//
//            if (nsDTO.getAll().isEmpty()) {
//                java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\rest\\ns.txt");
//                System.out.println(path);
//
//                BufferedReader reader = Files.newBufferedReader(path);
//                String line = reader.readLine();
//
//                while (line != null) {
//
//                    String[] name = line.split("\\s+");
//                    System.out.println(name[0]);
//                    System.out.println(name[1]);
//                    System.out.println(name[2]);
//
//                    line = reader.readLine();
//                    System.out.println(line);
//
//                    ns = new Namespace(name[0], name[1], name[2]);
//
//                    try {
//                        em.getTransaction().begin();
//                        em.persist(ns);
//                        em.getTransaction().commit();
//                    } finally {
//                        // em.close();
//                        nsDTO = FACADE.getAllNamespaces();
//                    }
//                }
//            }
//
//        } catch (NoConnectionException ex) {
//            Logger.getLogger(KubernetesResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return GSON.toJson(nsDTO);
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

//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = EMF.createEntityManager();
//
//        Deployment deployment;
//        DeploymentsDTO deploymentsDTO = null;
//
//        try {
//            deploymentsDTO = FACADE.getAllDeployments();
//
//            if (deploymentsDTO.getAll().isEmpty()) {
//
//                java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\rest\\deploy.txt");
//                System.out.println(path);
//
//                BufferedReader reader = Files.newBufferedReader(path);
//                String line = reader.readLine();
//
//                while (line != null) {
//
//                    String[] name = line.split("\\s+");
//                    System.out.println(name[0]);
//                    System.out.println(name[1]);
//                    System.out.println(name[2]);
//                    System.out.println(name[3]);
//                    System.out.println(name[4]);
//                    System.out.println(name[5]);
//                    System.out.println(name[6]);
//                    System.out.println(name[7]);
//                    System.out.println(name[8]);
//
//                    line = reader.readLine();
//                    System.out.println(line);
//
//                    deployment = new Deployment(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7], name[8]);
//                    try {
//                        em.getTransaction().begin();
//                        em.persist(deployment);
//                        em.getTransaction().commit();
//                    } finally {
//                        //em.close();
//                        deploymentsDTO = FACADE.getAllDeployments();
//                    }
//                }
//            }
//        } catch (NoConnectionException ex) {
//            Logger.getLogger(KubernetesResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return GSON.toJson(deploymentsDTO);
        DeploymentsDTO deploymentsDTO = FACADE.getAllDeployments();
        return GSON.toJson(deploymentsDTO);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pods")
    public String getPods() throws IOException, NoConnectionException {

//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = EMF.createEntityManager();
//
//        Pod pod;
//        PodsDTO podsDTO = null;
//
//        try {
//            podsDTO = FACADE.getAllPods();
//
//            if (podsDTO.getAll().isEmpty()) {
//
//                java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\rest\\pod.txt");
//                System.out.println(path);
//
//                BufferedReader reader = Files.newBufferedReader(path);
//                String line = reader.readLine();
//
//                while (line != null) {
//
//                    String[] name = line.split("\\s+");
//                    System.out.println(name[0]);
//                    System.out.println(name[1]);
//                    System.out.println(name[2]);
//                    System.out.println(name[3]);
//                    System.out.println(name[4]);
//                    System.out.println(name[5]);
//                    System.out.println(name[6]);
//                    System.out.println(name[7]);
//
//                    line = reader.readLine();
//                    System.out.println(line);
//
//                    pod = new Pod(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7]);
//
//                    try {
//                        em.getTransaction().begin();
//                        em.persist(pod);
//                        em.getTransaction().commit();
//                    } finally {
//                        //em.close();
//                        podsDTO = FACADE.getAllPods();
//                    }
//                }
//            }
//        } catch (NoConnectionException ex) {
//            Logger.getLogger(KubernetesResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return GSON.toJson(podsDTO);
        PodsDTO podsDTO = FACADE.getAllPods();
        return GSON.toJson(podsDTO);
    }

//*****************************************************************
// Old code bits for references if anythings breaks - DELETE LATER*
//*****************************************************************
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("namespaces")
//    public String getNamespaces() throws IOException, NoConnectionException {
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = emf.createEntityManager();
//
//        //  Namespace ns;
//        NamespacesDTO nsDTO = null;
//
//        List<List<String>> nodeInfo = new ArrayList<>();
//        List<String> nodeName = new ArrayList<>();
//        List<String> nodeStatus = new ArrayList<>();
//        List<String> nodeAge = new ArrayList<>();
//
//        java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\rest\\ns.txt");
//        System.out.println(path);
//        //  String read = Files.readAllLines(path).get(2);
//        BufferedReader reader = Files.newBufferedReader(path);
//        String line = reader.readLine();
//        while (line != null) {
//            //  String name = line.replaceAll("\\s+", " ");
//            String[] name = line.split("\\s+");
//            //    System.out.println(name[0]);
//            //    System.out.println(name[1]);
//            //    System.out.println(name[2]);
//            //    line = reader.readLine();
////            System.out.println(line);
//            line = reader.readLine();
//
////            String[] name = line.split("\t ");
////            String[] status = line.split("\t ");
////            String[] age = line.split("\t ");
//            nodeName.add(name[0]);
//            nodeStatus.add(name[1]);
//            nodeAge.add(name[2]);
//
////            ns = new Namespace(name[0], name[1], name[2]);
////            try {
////                em.getTransaction().begin();
////                em.persist(ns);
////                em.getTransaction().commit();
////            } finally {
////                //em.close();
////            }
//        }
//        Collections.addAll(nodeInfo, nodeName, nodeStatus, nodeAge);
//        System.out.println("Node info list: " + nodeInfo);
//
//        try {
//            nsDTO = FACADE.getAllNamespaces();
//        } catch (NoConnectionException ex) {
//            Logger.getLogger(KubernetesResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return GSON.toJson(nsDTO);
//
//    }
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("namespaces")
//    public List getNamespaces() throws IOException {
//        File ns = new File("ns.txt");
////        Scanner sc = new Scanner(ns);
//        BufferedReader reader = new BufferedReader(new FileReader(ns));
//        List<List<String>> nodeInfo = new ArrayList<>();
//        List<String> nodeName = new ArrayList<>();
//        List<String> nodeStatus = new ArrayList<>();
//        List<String> nodeAge = new ArrayList<>();
//
//        System.out.println("hello?");
//
//        String textFile;
//        while ((textFile = reader.readLine()) != null) {
//
//            StringTokenizer stn = new StringTokenizer(textFile);
//            String name = stn.nextToken();
//            String status = stn.nextToken();
//            String age = stn.nextToken();
//            System.out.println("name: " + name);
////            System.out.println("status: " + status);
////            System.out.println("age:" + age);
////            System.out.println(test);
////            
////            String[] newString = test.split("\\s+");
////
////            for ( String ss: newString) {
////                System.out.println(ss);
////            }
////
//            nodeName.add(name);
//            nodeStatus.add(status);
//            nodeAge.add(age);
//
//        }
////        nodeInfo.add(nodeName, nodeStatus, nodeAge);
////nodeName.add(sc.next());
////        while (sc.hasNext()) {
////            if ("NAME".equals(sc.next())) {
////                nodeName.add(sc.next());
////
////            }
////        }
////
////        for (String name : nodeName) {
////            System.out.println(name);
////        }
//        Collections.addAll(nodeInfo, nodeName, nodeStatus, nodeAge);
//        System.out.println("Node info list: " + nodeInfo);
//        return nodeName;
//    }
}

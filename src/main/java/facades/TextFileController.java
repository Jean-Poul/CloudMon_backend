package facades;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.ws.rs.Path;

// Class for testing if we can read from files
public class TextFileController {

    public static void main(String[] args) {
        try {
            readFromFile();
        } catch (IOException ex) {
            Logger.getLogger(TextFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Reading from file
    public static void readFromFile() throws IOException {
        List<String> nodeName = new ArrayList<>();
        List<String> nodeStatus = new ArrayList<>();
        List<String> nodeAge = new ArrayList<>();

        // Path to file
        Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\utils\\files\\ns.txt");
        //  System.out.println(path);
        //  String read = Files.readAllLines(path).get(2);

        // Read file from path
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        while (line != null) {
            //  String name = line.replaceAll("\\s+", " ");
            // Split Strings on white space
            String[] name = line.split("\\s+");
            //    System.out.println(name[0]);
            //    System.out.println(name[1]);
            //    System.out.println(name[2]);
            //    line = reader.readLine();
            //    System.out.println(line);

            line = reader.readLine();

            //    String[] name = line.split("\t ");
            //    String[] status = line.split("\t ");
            //    String[] age = line.split("\t ");
            nodeName.add(name[0]);
            nodeStatus.add(name[1]);
            nodeAge.add(name[2]);
        }

        System.out.println(nodeName.toString());

    }

//    Old way of testing if we could read from a file
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @javax.ws.rs.Path("namespaces")
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
////        nodeName.add(sc.next());
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

//    Method from when we were reading from resource and populating DB if it was empty
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @javax.ws.rs.Path("/namespaces")
//    public String getNamespaces() throws IOException, NoConnectionException {
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
//    }


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

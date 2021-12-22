package utils;

import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import entities.Deployment;
import entities.Namespace;
import entities.Pod;
import entities.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestData {

    public static void main(String[] args) throws IOException {
        getNamespaces();
        getServices();
        getDeployments();
        getPods();
    }

    public static void getNamespaces() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Namespace namespace;

        NamespacesDTO nDTO = null;

        nDTO = new NamespacesDTO(em.createNamedQuery("Namespace.getAllRows").getResultList());

        if (nDTO.getAll().isEmpty()) {
            // java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\utils\\files\\ns.txt");
            java.nio.file.Path path = Paths.get("./src/main/java/utils/files/ns.txt");
            System.out.println(path);

            String basePath = new java.io.File("").getAbsolutePath();
            System.out.println("BASESESE: " + basePath);

            System.out.println("PATHATHATH " + path.getFileName());
            BufferedReader reader = Files.newBufferedReader(path);
            String line = reader.readLine();

            while (line != null) {

                String[] name = line.split("\\s+");
                System.out.println(name[0]);
                System.out.println(name[1]);
                System.out.println(name[2]);

                line = reader.readLine();
                System.out.println(line);

                namespace = new Namespace(name[0], name[1], name[2]);

                try {
                    em.getTransaction().begin();
                    em.persist(namespace);
                    em.getTransaction().commit();
                } finally {
                    //em.close();
                }
            }
        }
    }

    public static void getServices() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Service service;
        ServicesDTO servicesDTO = null;

        servicesDTO = new ServicesDTO(em.createNamedQuery("Service.getAllRows").getResultList());

        if (servicesDTO.getAll().isEmpty()) {
            java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\utils\\files\\svc.txt");
            System.out.println(path);

            BufferedReader reader = Files.newBufferedReader(path);
            String line = reader.readLine();

            while (line != null) {

                String[] name = line.split("\\s+");
                System.out.println(name[0]);
                System.out.println(name[1]);
                System.out.println(name[2]);
                System.out.println(name[3]);
                System.out.println(name[4]);
                System.out.println(name[5]);
                System.out.println(name[6]);
                System.out.println(name[7]);

                line = reader.readLine();
                System.out.println(line);

                service = new Service(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7]);

                try {
                    em.getTransaction().begin();
                    em.persist(service);
                    em.getTransaction().commit();
                } finally {
                    //em.close();
                    // servicesDTO = (ServicesDTO) em.createNamedQuery("Service.getAllRows").getResultList();
                    // servicesDTO = instance.getAllServices();
                }
            }
        }
    }

    public static void getDeployments() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Deployment deployment;
        DeploymentsDTO dDTO = null;

        dDTO = new DeploymentsDTO(em.createNamedQuery("Deployment.getAllRows").getResultList());

        if (dDTO.getAll().isEmpty()) {
            java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\utils\\files\\deploy.txt");
            System.out.println(path);

            BufferedReader reader = Files.newBufferedReader(path);
            String line = reader.readLine();

            while (line != null) {

                String[] name = line.split("\\s+");
                System.out.println(name[0]);
                System.out.println(name[1]);
                System.out.println(name[2]);
                System.out.println(name[3]);
                System.out.println(name[4]);
                System.out.println(name[5]);
                System.out.println(name[6]);
                System.out.println(name[7]);
                System.out.println(name[8]);

                line = reader.readLine();
                System.out.println(line);

                deployment = new Deployment(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7], name[8]);

                try {
                    em.getTransaction().begin();
                    em.persist(deployment);
                    em.getTransaction().commit();
                } finally {
                    //em.close();
                }
            }
        }
    }

    public static void getPods() throws IOException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Pod pod;
        PodsDTO pDTO = null;

        pDTO = new PodsDTO(em.createNamedQuery("Pod.getAllRows").getResultList());

        if (pDTO.getAll().isEmpty()) {
            java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\utils\\files\\pod.txt");
            System.out.println(path);

            BufferedReader reader = Files.newBufferedReader(path);
            String line = reader.readLine();

            while (line != null) {

                String[] name = line.split("\\s+");
                System.out.println(name[0]);
                System.out.println(name[1]);
                System.out.println(name[2]);
                System.out.println(name[3]);
                System.out.println(name[4]);
                System.out.println(name[5]);
                System.out.println(name[6]);
                System.out.println(name[7]);
                System.out.println(name[8]);

                line = reader.readLine();
                System.out.println(line);

                pod = new Pod(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7]);

                try {
                    em.getTransaction().begin();
                    em.persist(pod);
                    em.getTransaction().commit();
                } finally {
                    // em.close();
                }
            }
        }
    }

}

package facades;

import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import entities.Service;
import errorhandling.NoConnectionException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import rest.KubernetesResource;

public class KubernetesFacade {

    private static EntityManagerFactory emf;
    private static KubernetesFacade instance;

    private KubernetesFacade() {

    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static KubernetesFacade getKubernetesFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new KubernetesFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public NamespacesDTO getAllNamespaces() throws NoConnectionException {

        EntityManager em = getEntityManager();

        try {
            return new NamespacesDTO(em.createNamedQuery("Namespace.getAllRows").getResultList());
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde

            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }

    }

    public ServicesDTO getAllServices() throws NoConnectionException {

        EntityManager em = getEntityManager();

        try {
            return new ServicesDTO(em.createNamedQuery("Service.getAllRows").getResultList());
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }

//        EntityManager em = getEntityManager();
//
//        Service service;
//        ServicesDTO servicesDTO = null;
//
//        try {
//
//            servicesDTO = new ServicesDTO(em.createNamedQuery("Service.getAllRows").getResultList());
////return new ServicesDTO(em.createNamedQuery("Service.getAllRows").getResultList());
//            //   servicesDTO = instance.getAllServices();
//
//            if (servicesDTO.getAll().isEmpty()) {
//
//                java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\facades\\files\\svc.txt");
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
//                    service = new Service(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7]);
//
//                    try {
//                        em.getTransaction().begin();
//                        em.persist(service);
//                        em.getTransaction().commit();
//                    } finally {
//                        //em.close();
//                         servicesDTO = (ServicesDTO) em.createNamedQuery("Service.getAllRows").getResultList();
//                       // servicesDTO = instance.getAllServices();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Logger.getLogger(KubernetesFacade.class.getName()).log(Level.SEVERE, null, e);
//            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
//            throw new NoConnectionException("No connection to the database");
//
//        } finally {
//          //  em.close();
//        }
//        return servicesDTO;

//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = EMF.createEntityManager();
//
//        Service service;
//        ServicesDTO servicesDTO = null;
//
//        try {
//            servicesDTO = FACADE.getAllServices();
//
//            if (servicesDTO.getAll().isEmpty()) {
//
//                java.nio.file.Path path = Paths.get("C:\\Users\\jplm\\Desktop\\Afsluttende datamatiker eksamen\\Backend\\src\\main\\java\\rest\\svc.txt");
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
//                    service = new Service(name[0], name[1], name[2], name[3], name[4], name[5], name[6], name[7]);
//
//                    try {
//                        em.getTransaction().begin();
//                        em.persist(service);
//                        em.getTransaction().commit();
//                    } finally {
//                        //em.close();
//                        servicesDTO = FACADE.getAllServices();
//                    }
//                }
//            }
//
//        } catch (NoConnectionException ex) {
//            Logger.getLogger(KubernetesResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public DeploymentsDTO getAllDeployments() throws NoConnectionException {

        EntityManager em = getEntityManager();

        try {
            return new DeploymentsDTO(em.createNamedQuery("Deployment.getAllRows").getResultList());
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
    }

    public PodsDTO getAllPods() throws NoConnectionException {

        EntityManager em = getEntityManager();

        try {
            return new PodsDTO(em.createNamedQuery("Pod.getAllRows").getResultList());
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }

    }

}

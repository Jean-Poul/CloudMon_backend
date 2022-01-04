package facades;

import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import errorhandling.NoConnectionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class KubernetesFacade {

    private static EntityManagerFactory emf;
    private static KubernetesFacade instance;

    // Constructor
    private KubernetesFacade() {

    }

    /**
     * Singleton
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

    // Getter
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Get all namespaces
    public NamespacesDTO getAllNamespaces() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new NamespacesDTO(em.createNamedQuery("Namespace.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // Get all services
    public ServicesDTO getAllServices() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new ServicesDTO(em.createNamedQuery("Service.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // Get all deployments
    public DeploymentsDTO getAllDeployments() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new DeploymentsDTO(em.createNamedQuery("Deployment.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // Get all pods
    public PodsDTO getAllPods() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new PodsDTO(em.createNamedQuery("Pod.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

}

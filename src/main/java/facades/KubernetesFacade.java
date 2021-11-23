package facades;

import dto.DeploymentsDTO;
import dto.NamespacesDTO;
import dto.PodsDTO;
import dto.ServicesDTO;
import errorhandling.NoConnectionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jplm
 */
public class KubernetesFacade {

    private static EntityManagerFactory emf;
    private static KubernetesFacade instance;

//Skal nok slettes men er her hvis noget går galt
//    private KubernetesFacade() {
//    }
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

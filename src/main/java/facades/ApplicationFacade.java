package facades;

import dto.ApplicationDTO;
import entities.Application;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 *
 * @author jplm
 */
public class ApplicationFacade {

    private static EntityManagerFactory emf;
    private static ApplicationFacade instance;

    private ApplicationFacade() {

    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static ApplicationFacade getApplicationFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ApplicationFacade();
        }
        return instance;
    }

    public ApplicationDTO addApp(String name, String version, String location) throws NoConnectionException {
        EntityManager em = emf.createEntityManager();

        Application app = new Application(name, version, location);

        try {
            em.getTransaction().begin();
            em.persist(app);
            em.getTransaction().commit();
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde

            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
        return new ApplicationDTO(app);
    }

}

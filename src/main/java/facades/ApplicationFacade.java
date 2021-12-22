package facades;

import dto.ApplicationDTO;
import dto.ApplicationsDTO;
import entities.Application;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getApplicationCount() throws NoConnectionException {
        EntityManager em = getEntityManager();
// EntityManager em = emf.createEntityManager();
        try {
            long appCount = (long) em.createQuery("SELECT COUNT(a) FROM Application a").getSingleResult();
            return appCount;
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
    }

    public ApplicationsDTO getAllApplications() throws NoConnectionException {
        EntityManager em = getEntityManager();

        try {
            return new ApplicationsDTO(em.createNamedQuery("Application.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }

    }

    public ApplicationDTO addApplication(String name, String version, String location) throws NoConnectionException {
        EntityManager em = getEntityManager();
//EntityManager em = emf.createEntityManager();

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

    public ApplicationDTO deleteApplication(long id) throws NotFoundException {
        EntityManager em = getEntityManager();
//EntityManager em = emf.createEntityManager();

        Application app = em.find(Application.class, id);

        if (app == null) {
            throw new NotFoundException("Could not delete, provided id does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(app);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new ApplicationDTO(app);
        }
    }

    public ApplicationDTO updateApplication(ApplicationDTO a) throws NotFoundException {
        System.out.println("FACADE app: " + "NAME: " + a.getName() + " VERSION: " + a.getVersion() + " ID: " + a.getId());

        if (isNameInvalid(a.getId(), a.getName(), a.getVersion(), a.getLocation())) {
            //  throw new MissingInputException("Name, email, password or phone is missing");
            throw new NotFoundException("Name, version or location is missing");
        }

        EntityManager em = getEntityManager();
//EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Application app = em.find(Application.class, a.getId());
            if (app == null) {
                throw new NotFoundException("No application with the provided id was found");
            } else {
                app.setName(a.getName());
                app.setVersion(a.getVersion());
                app.setLocation(a.getLocation());
            }
            em.getTransaction().commit();
            return new ApplicationDTO(app);
        } finally {
            em.close();
        }
    }

    public ApplicationDTO getApplication(long appId) throws NotFoundException {
        EntityManager em = getEntityManager();
        //EntityManager em = emf.createEntityManager();

        Application app = em.find(Application.class, appId);

        if (app == null) {
            throw new NotFoundException("No App with provided App name found");
        } else {
            try {
                return new ApplicationDTO(app);
            } finally {
                em.close();
            }
        }
    }

// Validation
    private static boolean isNameInvalid(long id, String name, String version, String location) {
        return (id == 0) || (name.length() == 0) || (version.length() == 0) || (location.length() == 0);
    }

}

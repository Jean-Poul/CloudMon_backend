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

    // Constructor
    private ApplicationFacade() {

    }

    /**
     * Singleton
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

    // Getter
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Application count
    public long getApplicationCount() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            long appCount = (long) em.createQuery("SELECT COUNT(a) FROM Application a").getSingleResult();
            return appCount;
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // List of all applications
    public ApplicationsDTO getAllApplications() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new ApplicationsDTO(em.createNamedQuery("Application.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }

    }

// Get an application with a given id
    public ApplicationDTO getApplication(long appId) throws NotFoundException {
        // Database connection
        EntityManager em = getEntityManager();

        // Find application
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

    // Add an application
    public ApplicationDTO addApplication(String name, String version, String location) throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        Application app = new Application(name, version, location);

        try {
            em.getTransaction().begin();
            em.persist(app);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
        return new ApplicationDTO(app);
    }

    // Delete application with an id
    public ApplicationDTO deleteApplication(long id) throws NotFoundException {
        // Database connection
        EntityManager em = getEntityManager();

        // Find application to delete
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

    // Update application
    public ApplicationDTO updateApplication(ApplicationDTO a) throws NotFoundException {

        // Validating input
        if (isNameInvalid(a.getId(), a.getName(), a.getVersion(), a.getLocation())) {
            throw new NotFoundException("Name, version or location is missing");
        }

        // Database connection
        EntityManager em = getEntityManager();

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

    // Validation method
    private static boolean isNameInvalid(long id, String name, String version, String location) {
        return (id == 0) || (name.length() == 0) || (version.length() == 0) || (location.length() == 0);
    }

}

package facades;

import dto.UserDTO;
import dto.UsersDTO;
import entities.Role;
import entities.User;
import errorhandling.NoConnectionException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    // Constructor
    private UserFacade() {

    }

    /**
     * Singleton
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    // Getter
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // User count
    public long getUserCount() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            long userCount = (long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            return userCount;
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // Get a verified user (used in loginendpoint.java)
    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        // Database connection
        EntityManager em = getEntityManager();

        User user;

        try {
            // Find user
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    // Get all users
    public UsersDTO getAllUsers() throws NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        try {
            return new UsersDTO(em.createNamedQuery("User.getAllRows").getResultList());
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
    }

    // Get a user
    public UserDTO getUser(String userName) throws NotFoundException, NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        // Find user
        User u = em.find(User.class, userName);

        if (u == null) {
            throw new NotFoundException("No user with provided username found");
        } else {
            try {
                return new UserDTO(u);
            } catch (Exception e) {
                throw new NoConnectionException("No connection to the database. " + e);
            } finally {
                em.close();
            }
        }
    }

    // Add a user
    public UserDTO addUser(String userName, String password) throws AuthenticationException, NoConnectionException {
        // Database connection
        EntityManager em = getEntityManager();

        User user;

        try {
            // Find and add user
            user = em.find(User.class, userName);
            if (user == null && userName.length() > 0 && password.length() > 0) {
                user = new User(userName, password);
                Role userRole = em.find(Role.class, "user");
                user.addRole(userRole);
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } else {
                if ((userName.length() == 0 || password.length() == 0)) {
                    throw new AuthenticationException("Missing input");
                }
                if (user.getUserName().equalsIgnoreCase(userName)) {
                    throw new AuthenticationException("Username is in use");
                }
            }
        } catch (Exception e) {
            throw new NoConnectionException("No connection to the database. " + e);
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

}

package facades;

import dto.UserDTO;
import dto.UsersDTO;
import entities.Role;
import entities.User;
import errorhandling.NoConnectionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
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

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getUserCount() {
        EntityManager em = getEntityManager();
// EntityManager em = emf.createEntityManager();
        try {
            long userCount = (long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            return userCount;
        } finally {
            em.close();
        }
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();
//EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO addUser(String userName, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();
//EntityManager em = emf.createEntityManager();
        User user;

        try {
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
                    throw new AuthenticationException("User exist");
                }
            }
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UsersDTO getAllUsers() throws NoConnectionException {
        EntityManager em = getEntityManager();
        //EntityManager em = emf.createEntityManager();

        try {
            return new UsersDTO(em.createNamedQuery("User.getAllRows").getResultList());
        } catch (Exception e) {
            // SKAL HUSKE AT LAVE DENNE EXCEPTION RIGTIG I ERRORHANDLING - Mangler stadig noget arbejde
            throw new NoConnectionException("No connection to the database");
        } finally {
            em.close();
        }
    }

}

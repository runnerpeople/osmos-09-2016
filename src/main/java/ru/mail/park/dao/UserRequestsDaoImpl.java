package ru.mail.park.dao;

import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.mail.park.model.UserProfile;

@Repository
public class UserRequestsDaoImpl implements UserRequestsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserProfile getUserById(Long id) {
        return entityManager.find(UserProfile.class,id);
    }


    @Override
    public Long addNewUser(String name, String login, String password, String email) {
        final UserProfile user = new UserProfile(name, login , email, password);
        entityManager.persist(user);
        return user.getId();
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return getAllUsers(10);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserProfile> getAllUsers(Integer limit_number) {
        List<UserProfile> users;
        try {
            final Query query = entityManager.createQuery("SELECT user FROM UserProfile user");
            users = query.setMaxResults(limit_number).getResultList();
        } catch (NoResultException nre) {
            final Logger log = Logger.getLogger(UserRequestsDaoImpl.class.getName());
            log.log(Level.WARNING,"NoResultException in getAllUsers");
            return null;
        }
        return users;
    }

    @Override
    public UserProfile existingUserByEmail(String email) {
        UserProfile user;
        try {
            user = entityManager.createQuery("SELECT user FROM UserProfile user"
                                                + " where user.email=:email", UserProfile.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            final Logger log = Logger.getLogger(UserRequestsDaoImpl.class.getName());
            log.log(Level.INFO, "NoResultException in duplicateEmail");
            user = null;
        }
        return user;
    }

    @Override
    public UserProfile existingUserByLogin(String login) {
        UserProfile user;
        try {
            user = entityManager.createQuery("SELECT user FROM UserProfile user"
                    + " where user.login=:login", UserProfile.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException nre) {
            final Logger log = Logger.getLogger(UserRequestsDaoImpl.class.getName());
            log.log(Level.INFO, "NoResultException in existingUserByLogin");
            user = null;
        }
        return user;
    }

    @Override
    public UserProfile checkingUserByLoginPassword(String login, String password) {
        UserProfile user;
        try {
            user = entityManager.createQuery("SELECT user FROM UserProfile user"
                    + " where user.login=:login" + " AND user.password=:password", UserProfile.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException nre) {
            final Logger log = Logger.getLogger(UserRequestsDaoImpl.class.getName());
            log.log(Level.INFO, "NoResultException in checkingUserByLogin");
            user = null;
        }
        return user;
    }

    @Override
    public Boolean removeUser(Long id) {
        UserProfile userProfile = entityManager.find(UserProfile.class, id);
        if (userProfile == null)
            return false;
        else {
            entityManager.remove(userProfile);
            return true;
        }
    }


}
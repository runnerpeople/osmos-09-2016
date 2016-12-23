package ru.mail.park.dao;


import org.springframework.stereotype.Repository;
import ru.mail.park.model.UserSession;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@Repository
public class SessionRequestsDaoImpl implements SessionRequestsDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long addNewSession(long user_id, String session_id, Date date_session, Byte is_auth) {
        final UserSession userSession = new UserSession(user_id,session_id,date_session,is_auth);
        entityManager.persist(userSession);
        return userSession.getId();
    }

    @Override
    public UserSession getSession(long user_id) {
        UserSession userSession;
        try {
            userSession = entityManager.createQuery("SELECT user FROM user_session user" +
                    "WHERE user.session_id=:userID",UserSession.class)
                    .setParameter("userID", user_id)
                    .getSingleResult();
        } catch (NoResultException nre) {
            final Logger log = Logger.getLogger(UserRequestsDaoImpl.class.getName());
            log.log(Level.INFO, "NoResultException in getSession");
            userSession = null;
        }
        return userSession;
    }

    @Override
    public UserSession update(long user_id, String session_id,Date date_session,Byte is_auth) {
        UserSession user = getSession(user_id);
        if (user == null) {
            addNewSession(user_id, session_id,date_session,is_auth);
            return getSession(user_id);
        }
        else {
            delete(user_id);
            addNewSession(user_id,session_id,date_session,is_auth);
            return getSession(user_id);
        }
    }

    @Override
    public Boolean delete(long user_id) {
        UserSession userSession = getSession(user_id);
        if (userSession == null)
            return false;
        else {
            entityManager.remove(userSession);
            return true;
        }
    }
}

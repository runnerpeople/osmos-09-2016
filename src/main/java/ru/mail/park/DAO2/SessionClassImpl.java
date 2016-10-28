package ru.mail.park.DAO2;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.mail.park.model.SessionClass;


@Repository
public class SessionClassImpl implements SessionClassDAO {

    public static String error_string = "";


    private SessionClass getSession(Integer id) {
        Session session = null;
        SessionClass sessionClass = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            sessionClass = session.load(SessionClass.class, id);
        } catch (Exception e) {
            error_string = e.getMessage();
            sessionClass = null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessionClass;
    }

    public SessionClass createSessionById(Integer user_id) {
        SessionClass sessionClass = new SessionClass(user_id);
        SessionClassCommit<SessionClass> commit = new SessionClassCommit<>();
        return commit.createSession(sessionClass);
    }

    public Integer getSessionById(Integer id) {
        SessionClass sessions = getSession(id);
        return (sessions == null) ? -1 : sessions.getUser_id();
    }

    public SessionClass getSessionObj(Integer id) {
        return getSession(id);
    }


    public boolean removeSessionById(Integer id) {
        SessionClass sessionClass = getSessionObj(id);
        SessionClassCommit<SessionClass> commit = new SessionClassCommit<>();
        Boolean status = commit.removeSession(sessionClass);
        if (!status)
            error_string = commit.error_string;
        return status;
    }
}
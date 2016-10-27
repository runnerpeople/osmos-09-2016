package ru.mail.park.Factory;

import ru.mail.park.DAO2.SessionClassDAO;
import ru.mail.park.DAO2.SessionClassImpl;
import ru.mail.park.DAO2.UserProfileImpl;
import ru.mail.park.DAO2.UserProfileDAO;
import ru.mail.park.model.SessionClass;

/**
 * Created by SergeyCheremisin on 25/10/2016.
 */
public class Factory {

    private static UserProfileDAO userProfielDAO = null;
    private static SessionClassDAO sessionClassDAO = null;
    private static SessionClassImpl sessionClassImpl = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public UserProfileDAO getUserProfileDAO() {
        if (userProfielDAO == null) {
            userProfielDAO = new UserProfileImpl();
        }
        return userProfielDAO;
    }

    public SessionClassDAO getSessionClassDAO() {
        if (sessionClassDAO == null) {
            sessionClassDAO = new SessionClassImpl();
        }
        return sessionClassDAO;
    }

}

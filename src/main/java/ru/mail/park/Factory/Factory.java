package ru.mail.park.Factory;

import ru.mail.park.DAO2.SessionClassDAO;
import ru.mail.park.DAO2.SessionClassImpl;
import ru.mail.park.DAO2.UserProfileImpl;
import ru.mail.park.DAO2.UserProfileDAO;

public class Factory {

    private static UserProfileDAO userProfielDAO = null;
    private static SessionClassDAO sessionClassDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if(instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public UserProfileDAO getUserProfileDAO(){
        if(userProfielDAO == null){
            userProfielDAO = new UserProfileImpl();
        }
        return userProfielDAO;
    }

    public SessionClassDAO getSessionClassDAO(){
        if(sessionClassDAO == null){
            sessionClassDAO = new SessionClassImpl();
        }
        return sessionClassDAO;
    }
}

package ru.mail.park.implementationDAO;

import org.springframework.stereotype.Repository;

import ru.mail.park.Factory.Factory;
import ru.mail.park.model.SessionClass;
import ru.mail.park.model.UserProfile;

import java.util.*;


@Repository
public class UserDao {


    public List getAllUsers() {
        return Factory.getInstance().getUserProfileDAO().getAllUsers();
    }


    public UserProfile getUserById(Integer id) {
        return Factory.getInstance().getUserProfileDAO().getUserById(id);
    }


    public boolean removeUserById(Integer id) {
        return Factory.getInstance().getUserProfileDAO().removeUser(id);
    }


    public UserProfile existingUserByLogin(String login) {
        return Factory.getInstance().getUserProfileDAO().existingUserByLogin(login);
    }


    public Integer addUser(String login, String name, String password, String email) {
        Integer id = Factory.getInstance().getUserProfileDAO().addNewUser(login, name, password, email);
        return id;
    }


    public SessionClass addSession(String login) {
        UserProfile userProfile = Factory.getInstance().getUserProfileDAO().existingUserByLogin(login);
        return Factory.getInstance().getSessionClassDAO().createSession(userProfile.getId());
    }


    public Integer getSessionById(Integer id) {
        return Factory.getInstance().getSessionClassDAO().getSessionById(id);
    }


    public boolean removeSessions(Integer id) {
        return Factory.getInstance().getSessionClassDAO().removeSessionById(id);
    }


}







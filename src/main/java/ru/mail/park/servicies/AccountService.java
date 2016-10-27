package ru.mail.park.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mail.park.implementationDAO.UserDao;
import ru.mail.park.model.SessionClass;
import ru.mail.park.model.UserProfile;

import java.util.*;


/**
 * Created by SergeyCheremisin on 22/09/16.
 */

@Service
public class AccountService {

    @Autowired
    private UserDao userDao;

    public List<UserProfile> getAllUsers() {
        return this.userDao.getAllUsers();
    }

    public UserProfile getUserById(Integer id) {
        return this.userDao.getUserById(id);
    }

    public boolean removeUserById(Integer id) {
        return this.userDao.removeUserById(id);
    }

    public UserProfile existingUserByLogin(String user) {

        return userDao.existingUserByLogin(user);
    }

    public SessionClass addSession(String login) {

        return userDao.addSession(login);
    }


    public Integer addUser(String login, String name, String password, String email) {
        return this.userDao.addUser(login, name, password, email);
    }


    public Integer getSessionById(Integer id){
        return userDao.getSessionById(id);
    }

    public boolean removeSessions(int id) {
        return this.userDao.removeSessions(id);
    }


}

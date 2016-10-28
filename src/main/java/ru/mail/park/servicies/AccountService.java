package ru.mail.park.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mail.park.implementationDAO.UserDao;
import ru.mail.park.model.SessionClass;
import ru.mail.park.model.UserProfile;

import java.util.*;

@Service
public class AccountService {

    @Autowired
    private UserDao userDao;

    public List getAllUsers() {
        return userDao.getAllUsers();
    }

    public UserProfile getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public boolean removeUserById(Integer id) {
        return userDao.removeUserById(id);
    }

    public UserProfile existingUserByLogin(String user) {
        return userDao.existingUserByLogin(user);
    }

    public SessionClass addSession(String login,String password) {
        return userDao.addSession(login,password);
    }

    public Integer addUser(String name, String login, String password, String email) {
        return userDao.addUser(name, login, password, email);
    }

    public Integer getSessionById(Integer id){
        return userDao.getSessionById(id);
    }

    public boolean removeSessions(int id) {
        return userDao.removeSessions(id);
    }

}

package ru.mail.park.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.dao.UserRequestsDAO;
import ru.mail.park.model.UserProfile;

import java.util.*;

@Transactional
@Service
public class AccountService {

    @Autowired
    private UserRequestsDAO userDao;

    public List getAllUsers() {
        return userDao.getAllUsers();
    }

    public List getAllUsers(Integer limit_number) {
        return userDao.getAllUsers(limit_number);
    }

    public UserProfile getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public boolean removeUserById(Long id) {
        return userDao.removeUser(id);
    }

    public UserProfile existingUserByLogin(String user) {
        return userDao.existingUserByLogin(user);
    }

    public UserProfile existingUserByEmail(String user) {
        return userDao.existingUserByEmail(user);
    }

    public UserProfile checkingUserByLoginPassword(String login,String password) {
        return userDao.checkingUserByLoginPassword(login,password);
    }

    public Long addUser(String name, String login, String password, String email) {
        return userDao.addNewUser(name, login, password, email);
    }


}

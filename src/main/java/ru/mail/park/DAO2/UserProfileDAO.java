package ru.mail.park.DAO2;

import ru.mail.park.model.UserProfile;

import java.util.List;


public interface UserProfileDAO {

    public Integer addNewUser(String name, String login, String email, String password);

    public List<UserProfile> getAllUsers();

    public UserProfile existingUserByLogin(String login);

    public UserProfile existingUserByLoginPassword(String login,String password);

    public UserProfile getUserById(Integer id);

    public Boolean removeUser(Integer id);

}
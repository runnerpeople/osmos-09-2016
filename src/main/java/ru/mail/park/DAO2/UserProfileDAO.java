package ru.mail.park.DAO2;

import ru.mail.park.model.UserProfile;

import java.util.List;


public interface UserProfileDAO {

    public Integer addNewUser(String login, String name, String email, String password);

    public List<UserProfile> getAllUsers();

    public UserProfile existingUserByLogin(String login);

    public UserProfile getUserById(Integer id);

    public Integer addNewSeesion(UserProfile user);

    public Boolean removeUser(Integer id);

}

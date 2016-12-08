package ru.mail.park.dao;

import ru.mail.park.model.UserProfile;

import java.util.List;


public interface UserRequestsDAO {

    Long addNewUser(String name, String login, String email, String password);

    List<UserProfile> getAllUsers();

    // For limit result_list //
    List<UserProfile> getAllUsers(Integer limit_number);

    UserProfile existingUserByEmail(String email);

    UserProfile existingUserByLogin(String login);

    UserProfile checkingUserByLoginPassword(String login,String password);

    UserProfile getUserById(Long id);

    Boolean removeUser(Long id);

}
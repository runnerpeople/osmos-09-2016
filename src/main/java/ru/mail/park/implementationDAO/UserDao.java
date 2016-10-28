package ru.mail.park.implementationDAO;

import org.springframework.stereotype.Repository;
import ru.mail.park.Factory.Factory;
import ru.mail.park.model.SessionClass;
import ru.mail.park.model.UserProfile;

import java.util.*;


@Repository
public class UserDao {

    public List<UserProfile> getAllUsers(){
        return Factory.getInstance().getUserProfileDAO().getAllUsers();
    }

    public UserProfile getUserById(Integer id){
        return Factory.getInstance().getUserProfileDAO().getUserById(id);
    }

    public boolean removeUserById(Integer id){
        return Factory.getInstance().getUserProfileDAO().removeUser(id);
    }

    public UserProfile existingUserByLogin(String login){
        return Factory.getInstance().getUserProfileDAO().existingUserByLogin(login);
    }

    public Integer addUser(String name, String login, String password, String email){
        return Factory.getInstance().getUserProfileDAO().addNewUser(name,login, password, email);
    }

    public SessionClass addSession(String login,String password){
        UserProfile userProfile = Factory.getInstance().getUserProfileDAO().existingUserByLoginPassword(login,password);
        if (userProfile == null)
            return null;
        else {
            return Factory.getInstance().getSessionClassDAO().createSessionById(userProfile.getId());
        }
    }

    public Integer getSessionById(Integer id) {
        return Factory.getInstance().getSessionClassDAO().getSessionById(id);
    }

    public boolean removeSessions(Integer id) {
        return Factory.getInstance().getSessionClassDAO().removeSessionById(id);
    }

}

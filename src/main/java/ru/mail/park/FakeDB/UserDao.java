package ru.mail.park.FakeDB;

import org.springframework.stereotype.Repository;
import ru.mail.park.Factory.Factory;
import ru.mail.park.model.SessionClass;
import ru.mail.park.model.UserProfile;
import ru.mail.park.model.UserSession;

import java.util.*;


@Repository
public class UserDao {


    private static Map<Integer, UserProfile> userProfiles;
    private Map<Integer, Integer> userSessions = new HashMap<Integer, Integer>();


    static {

        userProfiles = new HashMap<Integer, UserProfile>() {
            {
                put(0, new UserProfile("Sergey", "cheremisin.sergey@yandex.ru", "password11"));
                put(1, new UserProfile("Ilya", "nikitin.ilya@mail.ru", "password22"));
                put(2, new UserProfile("Ephrosiniya", "zerminova.phrosia@gmial.com", "password33"));
            }
        };
    }


    public List getAllUsers(){
        return Factory.getInstance().getUserProfileDAO().getAllUsers();
    }

    public UserProfile getUserById(Integer id){
        return Factory.getInstance().getUserProfileDAO().getUserById(id);
    }


    public boolean removeUserById(Integer id){
      return Factory.getInstance().getUserProfileDAO().removeUser(id);
    }



    public boolean existingUserById(Integer id) {
        return userProfiles.get(id) != null;
    }

    public UserProfile existingUserByLogin(String login){
        return Factory.getInstance().getUserProfileDAO().existingUserByLogin(login);
    }


    public Integer addUser(String login, String password, String email){
        return Factory.getInstance().getUserProfileDAO().addNewUser(login, password, email);
    }

    public UserSession getIdUserIfExist(String login) {
        final UserSession session = new UserSession();
        for (Map.Entry<Integer, UserProfile> entry : userProfiles.entrySet()) {
            final Integer key = entry.getKey();
            final UserProfile value = entry.getValue();
            if (login.equals(value.getLogin())) {
                session.setIdUser(key);
                return session;
            }
        }
        return null;
    }


    public SessionClass addSession(String login){
        UserProfile userProfile = Factory.getInstance().getUserProfileDAO().existingUserByLogin(login);
        return Factory.getInstance().getSessionClassDAO().createSession(userProfile.getId());
    }



    public Collection getSessions() {
        return userSessions.values();
    }

    public boolean removeSessions(int id) {
        if (userSessions.containsKey(id)) {
            userSessions.remove(id);
            return true;
        }
        return false;
    }

}







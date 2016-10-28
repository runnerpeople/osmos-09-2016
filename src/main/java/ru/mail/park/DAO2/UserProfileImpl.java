package ru.mail.park.DAO2;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.mail.park.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserProfileImpl implements UserProfileDAO {

    public static String error_string = "";

    public UserProfile getUserById(Integer id) {
        Session session = null;
        UserProfile userProfile = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            userProfile = session.load(UserProfile.class, id);
        } catch (Exception e) {
            error_string = e.getMessage();
            userProfile = null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userProfile;
    }

    public Integer addNewUser(String name, String login, String password, String email) {
        UserProfile user = new UserProfile(login, name, email, password);
        SessionClassCommit<UserProfile> commit = new SessionClassCommit<>();
        return commit.createSession(user).getId();
    }

    public UserProfile existingUser(String login,String password) {
        Session session = null;
        UserProfile user = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(UserProfile.class);
            criteria.add(Restrictions.eq("login", login));
            if (!password.equals(""))
                criteria.add(Restrictions.eq("password",password));
            user = (UserProfile) criteria.uniqueResult();
        } catch (Exception e) {
            error_string = e.getMessage();
            user = null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public UserProfile existingUserByLogin(String login) {
        return existingUser(login,"");
    }

    public UserProfile existingUserByLoginPassword(String login,String password) {
        return existingUser(login,password);
    }


    public List<UserProfile> getAllUsers() {
        Session session = null;
        List users = new ArrayList<UserProfile>();
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(UserProfile.class).list();
        } catch (Exception e) {
            error_string = e.getMessage();
            users = null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return (users == null || users.size()==0) ? null : users;
    }


    public Boolean removeUser(Integer id) {
        UserProfile userProfile = getUserById(id);
        SessionClassCommit<UserProfile> commit = new SessionClassCommit<>();
        Boolean status = commit.removeSession(userProfile);
        if (!status)
            error_string = commit.error_string;
        return status;
    }

}
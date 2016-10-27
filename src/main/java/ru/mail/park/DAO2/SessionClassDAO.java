package ru.mail.park.DAO2;


import ru.mail.park.model.SessionClass;

/**
 * Created by serqeycheremisin on 27/10/2016.
 */
public interface SessionClassDAO {

    public SessionClass createSession(Integer user_id);

    public SessionClass getUserById(Integer id);
}

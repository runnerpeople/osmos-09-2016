package ru.mail.park.DAO2;


import ru.mail.park.model.SessionClass;

public interface SessionClassDAO {

    public SessionClass createSession(Integer user_id);

    public Integer getSessionById(Integer id);

    public boolean removeSessionById(Integer id);

    public SessionClass getSessionObj(Integer id);
}

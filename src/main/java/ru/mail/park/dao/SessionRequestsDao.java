package ru.mail.park.dao;


import ru.mail.park.model.UserSession;

import java.util.Date;

public interface SessionRequestsDao {

    Long addNewSession(long user_id, String session_id, Date date_session, Byte is_auth);

    UserSession getSession(long user_id);

    UserSession update(long user_id,String session_id, Date date_session, Byte is_auth);

    Boolean delete(long user_id);
}

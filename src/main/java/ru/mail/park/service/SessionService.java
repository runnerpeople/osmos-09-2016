package ru.mail.park.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.dao.SessionRequestsDao;
import ru.mail.park.model.UserSession;

import java.util.Date;

@Transactional
@Service
public class SessionService {

    @Autowired
    private SessionRequestsDao sessionDao;

    public Long addSession(long user_id, String session_id, Date date_session, Byte is_auth) {
        return sessionDao.addNewSession(user_id,session_id,date_session,is_auth);
    }

    public UserSession findSessionByUserId(long user_id) {
        return sessionDao.getSession(user_id);
    }

    public void updateSession(long user_id, String session_id, Date date_session, Byte is_auth) {
        sessionDao.update(user_id,session_id,date_session,is_auth);
    }

    public Boolean deleteSessionByUserId(long user_id) {
        return sessionDao.delete(user_id);
    }
}

package ru.mail.park.DAO2;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.mail.park.model.SessionClass;

import javax.swing.*;

@Repository
public class SessionClassImpl implements SessionClassDAO {

    public SessionClass createSession(Integer user_id) {
        Session session = null;
        SessionClass sessionClass = new SessionClass(user_id);
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sessionClass);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return sessionClass;
    }


    public Integer getSessionById(Integer id) {
        Session session = null;
        SessionClass sessionClass = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            sessionClass = (SessionClass) session.load(SessionClass.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return sessionClass.getUser_id();
    }


    public SessionClass getSessionObj(Integer id) {
        Session session = null;
        SessionClass sessionClass = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            sessionClass = (SessionClass) session.load(SessionClass.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return sessionClass;
    }


    public boolean removeSessionById(Integer id) {
        SessionClass sessionClass = getSessionObj(id);
        Session session = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(sessionClass);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }
}

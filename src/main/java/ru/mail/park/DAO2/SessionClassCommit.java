package ru.mail.park.DAO2;

import org.hibernate.Session;

public class SessionClassCommit<T> {

    public String error_string = "";

    public T createSession(T value) {
        Session session = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(value);
            session.getTransaction().commit();
        } catch (Exception e) {
            error_string = e.getMessage();
            value = null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return value;
    }

    public boolean removeSession(T value) {
        Boolean error = false;
        Session session = null;
        try {
            session = HabernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(value);
            session.getTransaction().commit();
        } catch (Exception e) {
            error_string = e.getMessage();
            error = true;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return !error;
    }
}
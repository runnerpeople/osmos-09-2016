package ru.mail.park.model;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Table(name = "UserSession")
public class UserSession {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "session_id")
    private String session_id;

    @NotEmpty
    @Column(name = "date_session")
    @Temporal(TemporalType.DATE)
    private Date date_session;

    @NotEmpty
    @Column(name = "is_auth")
    private Byte is_auth;

    public UserSession(Long user_id, String session_id, Date date_session, Byte is_auth) {
        this.user_id = user_id;
        this.session_id = session_id;
        this.date_session = date_session;
        this.is_auth = is_auth;
    }

    public UserSession() {}

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_session() {
        return date_session;
    }

    public void setDate_session(Date date_session) {
        this.date_session = date_session;
    }

    public Byte getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(Byte is_auth) {
        this.is_auth = is_auth;
    }
}

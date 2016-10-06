package ru.mail.park.model;

/**
 * Created by SergeyCheremisin on 06/10/16.
 */
public class UserSession {
    private Integer idSession;
    private Integer idUser;

    public UserSession(){
        this.idSession = 0;
        this.idUser = 0;
    }

    public UserSession(Integer idSession, Integer idUser){
        this.idSession = idSession;
        this.idUser = idUser;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

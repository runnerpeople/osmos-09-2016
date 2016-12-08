package ru.mail.park.json;

public class SessionMessage {
    private String sessionid;
    private Long userid;

    public SessionMessage(String sessionid, Long userid) {
        this.sessionid = sessionid;
        this.userid = userid;
    }

    public String getSessionId() {
        return sessionid;
    }

    public Long getUserId() {
        return userid;
    }

    public void setSessionId(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }
}

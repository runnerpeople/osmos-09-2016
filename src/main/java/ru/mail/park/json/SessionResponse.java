package ru.mail.park.json;

public class SessionResponse {
    private Integer sessionid;
    private Integer userid;

    public SessionResponse(Integer sessionid, Integer userid) {
        this.sessionid = sessionid;
        this.userid = userid;
    }

    public Integer getSessionId() {
        return sessionid;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setSessionId(Integer sessionid) {
        this.sessionid = sessionid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }
}

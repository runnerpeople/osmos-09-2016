package ru.mail.park.ResponseInJson;

/**
 * Created by serqeycheremisin on 27/10/2016.
 */
public class SesstionResponse {
    private Integer sessionid;
    private Integer userid;

    public SesstionResponse(Integer sessionid, Integer userid) {
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

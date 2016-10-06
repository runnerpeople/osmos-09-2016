package ru.mail.park.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.FakeDB.View;

/**
 * Created by SergeyCheremisin on 30/09/16.
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

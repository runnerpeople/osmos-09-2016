package ru.mail.park.json;

public class GetSession {
    private Integer userid;

    public GetSession(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserId() {
        return userid;
    }
}
package ru.mail.park.json;

public class SuccessResponse {
    private String login;

    public SuccessResponse(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
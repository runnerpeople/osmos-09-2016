package ru.mail.park.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by SergeyCheremisin on 23/10/2016.
 */
public class UserProfileDTO {
    @NotEmpty
    private String login;

    @NotEmpty
    private String email;
    @NotEmpty

    private String password;


    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {

        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

package ru.mail.park.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;


import javax.persistence.*;


@Component
@Entity
@Proxy(lazy = false)
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "Login"),
        @UniqueConstraint(columnNames = "Email")})
public class UserProfile {

    @Id
    @Column(name = "User_id")
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @NotEmpty
    @Column(name = "Login")
    private String login;

    @Column(name = "Name")
    private String name;

    @NotEmpty
    @Column(name = "Email")
    private String email;
    @NotEmpty
    @Column(name = "Password")
    private String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserProfile(String login, String name, String email, String password) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserProfile() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

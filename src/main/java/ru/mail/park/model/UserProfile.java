package ru.mail.park.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "Users", uniqueConstraints = {
<<<<<<< HEAD
        @UniqueConstraint(columnNames = "login"),
        @UniqueConstraint(columnNames = "email") })
public class UserProfile {

    @Id
    @Column(name = "users_id")
=======
        @UniqueConstraint(columnNames = "Login"),
        @UniqueConstraint(columnNames = "Email") })
public class UserProfile {

    @Id
    @Column(name = "user_id")
>>>>>>> 84859dacbb78d97e350aff498af8c28b1a4934da
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "login")
    private String login;

<<<<<<< HEAD
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "email")
=======
    @Column(name = "Name")
    private String name;

    @NotEmpty
    @Column(name = "email",unique = true)
>>>>>>> 84859dacbb78d97e350aff498af8c28b1a4934da
    private String email;

    @JsonIgnore
    @NotEmpty
    @Column(name = "password")
    private String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserProfile(String name, String login, String email, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserProfile() {}

    public void setId(Long id) {
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

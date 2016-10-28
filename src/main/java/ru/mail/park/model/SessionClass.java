package ru.mail.park.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Component
@Entity
@Proxy(lazy = false)
@Table(name = "Sessions")
public class SessionClass {


    @Id
    @Column(name = "Session_id")
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(strategy= GenerationType.TABLE)
    Integer Session_id;

    @NotNull
    @Column
    Integer User_id;


    public SessionClass(Integer user_id) {
        User_id = user_id;
    }

    public SessionClass(){}

    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }

    public void setSession_id(Integer session_id) {
        Session_id = session_id;
    }

    public Integer getUser_id() {
        return User_id;
    }

    public Integer getSession_id() {
        return Session_id;
    }
}

package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class UserAccount {
    @Id
    @Column(unique = true, length = 64)
    private String user;

    @Type(type = "text")
    private String pass;

    public UserAccount(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public UserAccount() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "UserAccount [pass=" + pass + ", user=" + user + "]";
    }
}

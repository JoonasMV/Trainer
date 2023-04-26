package com.example.trainer.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 4L;
    private String username;
    private String id;

    private String password;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() { return id; }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id=" +id +
                '}';
    }
}

package com.example.trainer.diagram.api.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

@SuppressWarnings("unused")
public class User implements Serializable {

    private static final long serialVersionUID = 4L;
    private String username;

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

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

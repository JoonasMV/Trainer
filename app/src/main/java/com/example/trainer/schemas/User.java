package com.example.trainer.schemas;

public class User {
    private String username;
    private String _id;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getId() { return _id; }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id=" +_id +
                '}';
    }
}

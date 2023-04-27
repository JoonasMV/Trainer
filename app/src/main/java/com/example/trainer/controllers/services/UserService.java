package com.example.trainer.controllers.services;

import com.example.trainer.api.UserOperations;
import com.example.trainer.model.User;

import java.util.List;

public class UserService {

    private final UserOperations api;

    public UserService(UserOperations api){
        this.api = api;
    }

    public boolean register(User user) {
        return api.registerUser(user);
    }

    public boolean authenticate(User user) {
        return api.authenticateUser(user);
    }

    public void refresh() {
        api.refreshToken();
    }

    public User getUser() {
        return api.getUser();
    }

    public boolean sessionValid() {
        return api.sessionValid();
    }

    public List<String> getUsernames() {
        return api.getUsernames();
    }

    public void logOut() {
        api.logOut();
    }
}

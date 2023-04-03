package com.example.trainer.controllers.services;

import com.example.trainer.api.AuthOperations;
import com.example.trainer.api.AuthenticationException;
import com.example.trainer.model.User;

public class UserService {

    private final AuthOperations api;

    public UserService(AuthOperations api){
        this.api = api;
    }

    public void register(String username, String password) throws IllegalArgumentException {
        api.registerUser(username, password);
    }

    public void authenticate(String username, String password) throws IllegalArgumentException {
        api.authenticateUser(username, password);
    }

    public void refresh() throws AuthenticationException {
        api.refreshToken();
    }

    public User getUser() {
        return api.getUser();
    }
}

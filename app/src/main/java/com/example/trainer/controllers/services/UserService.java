package com.example.trainer.controllers.services;

import com.example.trainer.api.AuthOperations;
import com.example.trainer.api.AuthenticationException;
import com.example.trainer.model.User;

public class UserService {

    private final AuthOperations api;

    public UserService(AuthOperations api){
        this.api = api;
    }

    public void register(User user) throws IllegalArgumentException {
        api.registerUser(user);
    }

    public void authenticate(User user) throws IllegalArgumentException {
        api.authenticateUser(user);
    }

    public void refresh() throws AuthenticationException {
        api.refreshToken();
    }

    public User getUser() {
        return api.getUser();
    }
}

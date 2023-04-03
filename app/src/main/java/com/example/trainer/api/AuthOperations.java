package com.example.trainer.api;

import com.example.trainer.model.User;

public interface AuthOperations {
    void registerUser(String username, String password);

    void authenticateUser(String username, String password);

    void refreshToken();

    User getUser();
}

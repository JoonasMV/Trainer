package com.example.trainer.api;

import com.example.trainer.model.User;

public interface AuthOperations {
    void registerUser(User user);

    void authenticateUser(User user);

    void refreshToken();

    User getUser();
}

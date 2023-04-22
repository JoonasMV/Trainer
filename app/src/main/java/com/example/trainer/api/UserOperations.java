package com.example.trainer.api;

import com.example.trainer.model.User;

import java.util.List;

public interface UserOperations {
    void registerUser(User user);

    void authenticateUser(User user);

    void refreshToken();

    User getUser();

    boolean sessionValid();

    List<String> getUsernames();
}

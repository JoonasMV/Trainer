package com.example.trainer.api;

import com.example.trainer.model.User;

import java.util.List;

public interface UserOperations {
    boolean registerUser(User user);

    boolean authenticateUser(User user);

    void refreshToken();

    User getUser();

    boolean sessionValid();

    List<String> getUsernames();

    void logOut();
}

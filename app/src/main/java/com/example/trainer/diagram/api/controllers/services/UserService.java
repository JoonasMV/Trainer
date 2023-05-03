package com.example.trainer.diagram.api.controllers.services;

import com.example.trainer.diagram.api.UserOperations;
import com.example.trainer.diagram.api.model.User;

import java.util.List;

/**
 * This service uses the UserOperations interface to communicate with the api.
 * All methods in this class are synchronous.
 */
public class UserService {

    private final UserOperations api;

    public UserService(UserOperations api){
        this.api = api;
    }

    /**
     * Registers a user.
     * @param user User to register.
     * @return True if registration was successful, false otherwise.
     */
    public boolean register(User user) {
        return api.registerUser(user);
    }

    /**
     * Authenticates a user.
     * @param user User to authenticate.
     * @return True if authentication was successful, false otherwise.
     */
    public boolean authenticate(User user) {
        return api.authenticateUser(user);
    }

    /**
     * Refreshes the session token.
     */
    public void refresh() {
        api.refreshToken();
    }

    /**
     * Gets the current user.
     * @return Current user.
     */
    public User getUser() {
        return api.getUser();
    }

    /**
     * Checks if the session token is valid.
     * @return True if session token is valid, false otherwise.
     */
    public boolean sessionValid() {
        return api.sessionValid();
    }

    /**
     * Gets all usernames.
     * @return List of usernames.
     */
    public List<String> getUsernames() {
        return api.getUsernames();
    }

    /**
     * Logs out the current user.
     */
    public void logOut() {
        api.logOut();
    }
}

package com.example.trainer.api;

import com.example.trainer.model.User;

import java.util.List;

/**
 * Includes all operations required for User and authentication
 */
public interface UserOperations {

    /**
     * Registers user
     * @param user user to be registered
     * @return boolean true if registration was successful false if not
     */
    boolean registerUser(User user);

    /**
     * Authenticates user
     * @param user user to be authenticated
     * @return boolean true if authentication was successful false if not
     */
    boolean authenticateUser(User user);

    /**
     * Refreshes token if existing token is present
     */
    void refreshToken();

    /**
     * Gets the currently logged in user
     * @return User user
     */
    User getUser();

    /**
     * Tells if the session is valid
     * @return boolean true if session is valid false if not
     */
    boolean sessionValid();

    /**
     * Gets all usernames
     * @return List<String> list of usernames
     */
    List<String> getUsernames();

    /**
     * Logs out the user
     */
    void logOut();
}

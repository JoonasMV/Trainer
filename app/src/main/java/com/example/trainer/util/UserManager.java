package com.example.trainer.util;

import android.content.Context;

import com.example.trainer.model.User;
import com.google.gson.Gson;

/**
 * This class is used to manage the currently logged in user.
 * User is stored in shared preferences. This allows the user to stay logged in even if the app is closed.
 * This class should be used alongside with the {@link TokenManager}.
 */
public class UserManager {

    private User user;

    private final String fileKey = "trainer";
    private final Gson gson = new Gson();

    private final Context context;

    public UserManager(Context context){
        this.context = context;
    }

    /**
     * Returns the currently logged in user.
     * If the user is null it will try to read the user from shared preferences.
     * @return the currently logged in user or null
     */
    public User getUser(){
        if(user == null){
            user = readUserFromPref();
        }
        return user;
    }

    /**
     * Sets the currently logged in user.
     * @param user the user to set
     */
    public void setUser(User user){
        this.user = user;
        writeUserToPref(user);
    }

    /**
     * Checks if a user is currently logged in.
     * If the user is logged in it does not necessarily mean that the token is still valid.
     * @return true if a user is logged in, false otherwise
     */
    public boolean isUserLoggedIn(){
        return getUser() != null;
    }

    /**
     * Logs out the currently logged in user by clearing shared preferences and setting
     * the user to null.
     */
    public void logout(){
        clearUserPref();
        user = null;
    }

    /**
     * Writes the user to shared preferences.
     * @param user the user to write
     */
    public void writeUserToPref(User user){
        String json = serialize(user);
        context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit().putString("user", json).apply();
    }

    private String serialize(User user){
        return gson.toJson(user);
    }

    /**
     * Reads the user from shared preferences.
     * @return the user or null if no user is stored in shared preferences
     */
    public User readUserFromPref(){
        String json = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).getString("user", null);
        if(json == null){
            return null;
        }
        return deserialize(json);
    }

    private User deserialize(String json){
        return gson.fromJson(json, User.class);
    }

    /**
     * Clears the user from shared preferences.
     */
    public void clearUserPref(){
        context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit().putString("user", null).apply();
    }
}

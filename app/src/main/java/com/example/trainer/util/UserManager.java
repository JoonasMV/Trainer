package com.example.trainer.util;

import android.content.Context;

import com.example.trainer.model.User;
import com.google.gson.Gson;

public class UserManager {

    private User user;

    private final String fileKey = "trainer";
    private final Gson gson = new Gson();

    private final Context context;

    public UserManager(Context context){
        this.context = context;
    }

    public User getUser(){
        if(user == null){
            user = readUserFromPref();
        }
        return user;
    }

    public void setUser(User user){
        this.user = user;
        writeUserToPref(user);
    }

    public boolean isUserLoggedIn(){
        return getUser() != null;
    }

    public void logout(){
        clearUserPref();
        user = null;
    }

    public void writeUserToPref(User user){
        String json = serialize(user);
        context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit().putString("user", json).apply();
    }

    private String serialize(User user){
        return gson.toJson(user);
    }

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

    public void clearUserPref(){
        context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit().putString("user", null).apply();
    }


}

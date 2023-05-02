package com.example.trainer.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

/**
 * Utility class for managing the token.
 * The token is saved to the shared preferences. This allows the app to remember the token.
 */
public class TokenManager {
    private String token;
    private final Context context;

    public TokenManager(Context context){
        this.context = Objects.requireNonNull(context);
    }

    /**
     * Returns the token. If the token is not set, it is read from the shared preferences.
     * @return the token or null if the token is not set
     */
    public String getToken(){
        if(token == null){
            token = readFromPref();
        }
        return token;
    }

    private String readFromPref(){
        SharedPreferences pref = context.getSharedPreferences("trainer", Context.MODE_PRIVATE);
        return pref.getString("token", null);
    }

    /**
     * Saves the token to the shared preferences and sets it as the current token.
     * @param token the token to be saved
     */
    public void saveToken(String token){
        this.token = token;
        writeToPref(token);
    }

    private void writeToPref(String token){
        SharedPreferences pref = context.getSharedPreferences("trainer", Context.MODE_PRIVATE);
        pref.edit().putString("token", token).apply();
    }

    /**
     * Deletes the token from the shared preferences and sets the current token to null.
     */
    public void deleteToken() {
        this.token = null;
        SharedPreferences pref = context.getSharedPreferences("trainer", Context.MODE_PRIVATE);
        pref.edit().remove("token").apply();
    }
}

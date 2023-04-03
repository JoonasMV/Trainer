package com.example.trainer.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trainer.model.Workout;
import com.google.gson.Gson;

import java.util.Objects;

public class TokenManager {
    private String token;
    private final Context context;

    public TokenManager(Context context){
        this.context = Objects.requireNonNull(context);
    }

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

    public void saveToken(String token){
        this.token = token;
        writeToPref(token);
    }


    private void writeToPref(String token){
        SharedPreferences pref = context.getSharedPreferences("trainer", Context.MODE_PRIVATE);
        pref.edit().putString("token", token).commit();
    }
}

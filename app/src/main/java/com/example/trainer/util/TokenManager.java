package com.example.trainer.util;

import android.content.Context;

import java.util.Objects;

public class TokenManager {
    private String token;
    private Context context;

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
        return null;
    }

    public void saveToken(String token){
        this.token = token;
        writeToPref(token);
    }

    private void writeToPref(String token){

    }
}

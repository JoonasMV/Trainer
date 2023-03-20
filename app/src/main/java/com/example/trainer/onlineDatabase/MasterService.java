package com.example.trainer.onlineDatabase;

import com.google.gson.Gson;

import okhttp3.MediaType;

public abstract class MasterService implements DatabaseService{
    protected final Gson gson = new Gson();
    protected final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

}

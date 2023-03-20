package com.example.trainer.onlineDatabase;

import com.google.gson.Gson;

public abstract class MasterService implements DatabaseService{
    protected final Gson gson = new Gson();
}

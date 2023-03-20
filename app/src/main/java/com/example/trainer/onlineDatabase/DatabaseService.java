package com.example.trainer.onlineDatabase;

import okhttp3.OkHttpClient;

public interface DatabaseService {
    Object[] getAll(OkHttpClient okHttpClient);
    Object getOne(OkHttpClient okHttpClient, int id);
    Object getById(OkHttpClient okHttpClient);
}

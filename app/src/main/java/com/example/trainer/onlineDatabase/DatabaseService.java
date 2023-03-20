package com.example.trainer.onlineDatabase;

import okhttp3.OkHttpClient;

public interface DatabaseService {
    Object[] getAll(OkHttpClient okHttpClient);
    Object getById(OkHttpClient okHttpClient, int id);
    Object post(OkHttpClient okHttpClient, Object item);
}

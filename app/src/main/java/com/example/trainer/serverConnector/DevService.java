package com.example.trainer.serverConnector;

import com.example.trainer.util.Settings;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DevService {
    private final String URI_PATH = Settings.DB_URI + "/dev";

    OkHttpClient okHttpClient = new OkHttpClient();

    public void clearDatabase() {
        Request req = new Request.Builder()
                .delete()
                .url(URI_PATH + "/clear")
                .build();

        try {
            okHttpClient.newCall(req).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

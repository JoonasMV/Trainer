package com.example.trainer.onlineDatabase;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.trainer.database.schemas.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public User[] getAllUsers() {
        Future<User[]> allUsers = executor.submit(new getAllUserTask());
        return null;
    }

    class getAllUserTask implements Callable<User[]> {
        public User[] call() {
            Request req = new Request.Builder()
                    .url("http://192.168.1.103:8081/users")
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();
                String resString = res.body().string();

                Gson gson = new Gson();

                List<User> users;
                Type listtype = new TypeToken<List<User>>() {}.getType();

                users = gson.fromJson(resString, listtype);
                System.out.println(users.get(1).getUsername());

                User user = gson.fromJson(resString, User.class);
                System.out.println(user);

                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}

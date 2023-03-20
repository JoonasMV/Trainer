package com.example.trainer.onlineDatabase;

import com.example.trainer.Settings;
import com.example.trainer.database.schemas.User;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService extends MasterService {
    private final String URI = Settings.DB_URI + "/users";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static UserService instance = null;

    private UserService() {}

    public static UserService getInstance() {
        if(instance == null) instance = new UserService();
        return instance;
    }


    @Override
    public User[] getAll(OkHttpClient okHttpClient) {
        Future<User[]> future = executor.submit(() -> {
            Request req = new Request.Builder()
                    .url(URI)
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();
                String resString = res.body().string();

                List<User> userList;
                Type listType = new TypeToken<List<User>>() {
                }.getType();

                userList = gson.fromJson(resString, listType);
                return userList.toArray(new User[0]);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getAll()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getById(OkHttpClient okHttpClient, int id) {
        Future<User> future = executor.submit(() -> {
            Request req = new Request.Builder()
                    .url(URI + "/" + id)
                    .build();

            try {
                Response res = okHttpClient.newCall(req).execute();
                String resString = res.body().string();

                System.out.println(resString);
                System.out.println(gson.fromJson(resString, User.class));
                return gson.fromJson(resString, User.class);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getById()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object post(OkHttpClient okHttpClient, Object item) {
        Future<User> future = executor.submit(() -> {
        RequestBody reqBody = RequestBody.create(gson.toJson(item), JSON);

        Request req = new Request.Builder()
                .url(URI)
                .post(reqBody)
                .build();

        try {
            Response res = okHttpClient.newCall(req).execute();
            return gson.fromJson(res.body().string(), User.class);
        } catch (IOException e) {
            System.out.println("UserService() - post()");
            e.printStackTrace();
            return null;
        }
    });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService() - getById()");
            e.printStackTrace();
            return null;
        }
    }

}

package com.example.trainer.onlineDatabase;

import com.example.trainer.Settings;
import com.example.trainer.database.schemas.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserService extends MasterService {
    private final String URI = Settings.DB_URI + "/users";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static UserService instance = null;
//    private final Gson gson2 = new Gson();

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

//                Gson gson = new Gson();

                List<User> userList;
                Type listtype = new TypeToken<List<User>>() {
                }.getType();

                userList = gson.fromJson(resString, listtype);
                //System.out.println(userList);
                return userList.toArray(new User[0]);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getOne(OkHttpClient okHttpClient, int id) {
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
            return null;
        }
    }

    @Override
    public Object getById(OkHttpClient okHttpClient) {
        return null;
    }

}

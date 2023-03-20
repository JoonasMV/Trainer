package com.example.trainer.onlineDatabase;

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

public class UserService implements DatabaseService {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
//    private final Handler handler = new Handler(Looper.getMainLooper());

    private static UserService instance = null;

    private UserService() {}

    public static UserService getInstance() {
        if(instance == null) instance = new UserService();
        return instance;
    }


    @Override
    public User[] getAll() {
        Future<User[]> future = executor.submit(new getAllUserTask());
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("UserService()");
            e.printStackTrace();
            return null;
        }

        //return allUsers;
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

                List<User> userList;
                Type listtype = new TypeToken<List<User>>() {}.getType();

                userList = gson.fromJson(resString, listtype);
                //System.out.println(userList);
                return userList.toArray(new User[0]);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}

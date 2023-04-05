package com.example.trainer.api;

import android.content.Context;
import android.util.Log;

import com.example.trainer.api.responses.Token;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;
import com.example.trainer.util.TokenManager;
import com.example.trainer.util.UserManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class TrainerAPIWrapper extends API implements AuthOperations, ExerciseTypeOperations, WorkoutOperations {

    private final OkHttpClient client;
    private final TokenManager tokenManager;

    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Gson gson;

    private final UserManager userManager;

    public TrainerAPIWrapper(Context context){
        this.tokenManager = new TokenManager(context);
        this.userManager = new UserManager(context);
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
    }


    @Override
    public void registerUser(User user) {
        Log.d("TrainerAPIWrapper", "registerUser: " + user);
        Future<Token> result = executor.submit(() -> {
            RequestBody reqBody = RequestBody.create(gson.toJson(user), JSON);

            Request req = new Request.Builder()
                    .url(APIEndpoints.AUTH_URL + "/register")
                    .post(reqBody)
                    .build();

            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), Token.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });

        try {
            tokenManager.saveToken(result.get().getToken());
            userManager.setUser(user);
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            stopSession();
            e.printStackTrace();
        }
    }

    @Override
    public void authenticateUser(User user) {
        Future<Token> result = executor.submit(() -> {
            RequestBody reqBody = RequestBody.create(gson.toJson(user), JSON);

            Request req = new Request.Builder()
                    .url(APIEndpoints.AUTH_URL + "/authenticate")
                    .post(reqBody)
                    .build();

            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), Token.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });

        try {
            tokenManager.saveToken(result.get().getToken());
            userManager.setUser(user);
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            stopSession();
            e.printStackTrace();
        }
    }

    @Override
    public void refreshToken() {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public User getUser() {
        return userManager.getUser();
    }

    @Override
    public boolean sessionValid() {
        return tokenManager.getToken() != null;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        Future<Workout> result = executor.submit(() -> {
            RequestBody reqBody = RequestBody.create(gson.toJson(workout), JSON);
            String token = tokenManager.getToken();

            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + "/workouts")
                    .post(reqBody)
                    .header("Authorization", "Bearer " + token)
                    .build();

            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), Workout.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });


        try {
            return result.get();
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            stopSession();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Workout> getWorkouts() {
        Future<List<Workout>> result = executor.submit(() -> {
            String token = tokenManager.getToken();
            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + "/workouts")
                    .header("Authorization", "Bearer " + token)
                    .build();

            Type type = new TypeToken<List<Workout>>() {}.getType();
            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), type);
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        });
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
            stopSession();
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteWorkout(String id) {
        executor.submit(() -> {
            String token = tokenManager.getToken();
            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + "/workouts/" + id)
                    .header("Authorization", "Bearer " + token)
                    .delete()
                    .build();

            try {
                client.newCall(req).execute();
            } catch (IOException e) {
                stopSession();
            }
        });
    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        Future<List<ExerciseType>> result = executor.submit(() -> {
            String token = tokenManager.getToken();
            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + "/exercisetypes")
                    .header("Authorization", "Bearer " + token)
                    .build();

            Type type = new TypeToken<List<ExerciseType>>() {}.getType();
            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), type);
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        });
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException | IllegalStateException e) {
            e.printStackTrace();
            stopSession();
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteExerciseType(String id) {
        executor.submit(() -> {
            String token = tokenManager.getToken();
            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + "/exercisetypes/" + id)
                    .header("Authorization", "Bearer " + token)
                    .delete()
                    .build();

            try {
                client.newCall(req).execute();
            } catch (IOException e) {
                stopSession();
            }
        });

    }

    private void stopSession(){
        tokenManager.deleteToken();
        userManager.logout();
    }
}

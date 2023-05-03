package com.example.trainer.diagram.api;

import android.content.Context;
import android.util.Log;

import com.example.trainer.diagram.api.responses.Token;
import com.example.trainer.diagram.api.model.ExerciseType;
import com.example.trainer.diagram.api.model.User;
import com.example.trainer.diagram.api.model.Workout;
import com.example.trainer.diagram.api.util.TokenManager;
import com.example.trainer.diagram.api.util.UserManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Wrapper class for all of the API operations. To use methods that require authentication,
 * you must authenticate user first by calling {@link #authenticateUser(User)}. This class also manages the token.
 */
public class TrainerAPIWrapper extends API implements UserOperations, ExerciseTypeOperations,
        WorkoutOperations, QuoteOperations {

    /**
     * OkHttpClient used for making requests
     */
    private final OkHttpClient client;

    /**
     * TokenManager used for managing the token
     */
    private final TokenManager tokenManager;

    /**
     * MediaType used for JSON
     */
    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * Used for parsing JSON
     */
    private final Gson gson;

    /**
     * UserManager used for managing the user
     */
    private final UserManager userManager;

    public TrainerAPIWrapper(Context context) {
        this.tokenManager = new TokenManager(context);
        this.userManager = new UserManager(context);
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
    }

    public TrainerAPIWrapper(OkHttpClient client, TokenManager tokenManager, Gson gson, UserManager userManager) {
        this.client = client;
        this.tokenManager = tokenManager;
        this.gson = gson;
        this.userManager = userManager;
    }


    /**
     * {@inheritDoc}
     * <br>Username must be unique.
     */
    @Override
    public boolean registerUser(User user) {
        RequestBody reqBody = RequestBody.create(gson.toJson(user), JSON);

        Request req = new Request.Builder()
                .url(APIEndpoints.AUTH_URL + "/register")
                .post(reqBody)
                .build();

        try (Response res = client.newCall(req).execute()) {
            Token token = gson.fromJson(res.body().string(), Token.class);
            tokenManager.saveToken(token.getToken());
            userManager.setUser(user);
        } catch (IOException e) {
            Log.d("API", "Error while registering user: " + e.getMessage());
            stopSession();
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticateUser(User user) {
        RequestBody reqBody = RequestBody.create(gson.toJson(user), JSON);

        Request req = new Request.Builder()
                .url(APIEndpoints.AUTH_URL + "/authenticate")
                .post(reqBody)
                .build();

        try (Response res = client.newCall(req).execute()) {
            Token token = gson.fromJson(res.body().string(), Token.class);
            tokenManager.saveToken(token.getToken());
            userManager.setUser(user);
        } catch (IOException e) {
            Log.d("API", "Error while authenticating user: " + e.getMessage());
            stopSession();
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshToken() {
        RequestBody body = RequestBody.create("", JSON);
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.AUTH_URL + "/refresh")
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();
        try (Response res = client.newCall(req).execute()) {
            Token responseToken = gson.fromJson(res.body().string(), Token.class);
            tokenManager.saveToken(responseToken.getToken());
        } catch (IOException e) {
            Log.d("API", "Error while refreshing token: " + e.getMessage());
            stopSession();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser() {
        return userManager.getUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sessionValid() {
        return tokenManager.getToken() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUsernames() {
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.USERS_URL)
                .header("Authorization", "Bearer " + token)
                .build();

        Type type = new TypeToken<List<String>>() {
        }.getType();
        try (Response res = client.newCall(req).execute()) {
            return gson.fromJson(res.body().string(), type);
        } catch (IOException e) {
            Log.d("API", "Error while getting usernames: " + e.getMessage());
            stopSession();
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logOut() {
        stopSession();
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public Workout saveWorkout(Workout workout) {
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
            Log.d("API", "Error while saving workout: " + e.getMessage());
            stopSession();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public List<Workout> getSharedWorkouts(String username) {

            String token = tokenManager.getToken();
            Request req = new Request.Builder()
                    .url(APIEndpoints.USER_URL + String.format("/%s/workouts", username))
                    .header("Authorization", "Bearer " + token)
                    .build();
            Type type = new TypeToken<List<Workout>>() {}.getType();
            try (Response res = client.newCall(req).execute()) {
                return gson.fromJson(res.body().string(), type);
            } catch (IOException e) {
                Log.d("API", "Error while fetching workouts for user: " + username + e.getMessage());
                stopSession();
                return new ArrayList<>();
            }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public List<Workout> getWorkouts() {
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/workouts")
                .header("Authorization", "Bearer " + token)
                .build();

        Type type = new TypeToken<List<Workout>>() {
        }.getType();
        try (Response res = client.newCall(req).execute()) {
            return gson.fromJson(res.body().string(), type);
        } catch (IOException e) {
            Log.d("API", "Error while getting workouts: " + e.getMessage());
            stopSession();
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @SuppressWarnings("EmptyTryBlock")
    @Override
    public void deleteWorkout(String id) {
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/workouts/" + id)
                .header("Authorization", "Bearer " + token)
                .delete()
                .build();

        try (Response res = client.newCall(req).execute()) {
        } catch (IOException e) {
            Log.d("API", "Error while deleting workout: " + e.getMessage());
            stopSession();
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/exercisetypes")
                .header("Authorization", "Bearer " + token)
                .build();

        Type type = new TypeToken<List<ExerciseType>>() {
        }.getType();
        try (Response res = client.newCall(req).execute()) {
            return gson.fromJson(res.body().string(), type);
        } catch (IOException e) {
            Log.d("API", "Error while getting exercise types: " + e.getMessage());
            stopSession();
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public void deleteExerciseType(String id) {
        String token = tokenManager.getToken();
        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/exercisetypes/" + id)
                .header("Authorization", "Bearer " + token)
                .delete()
                .build();

        try {
            client.newCall(req).execute();
        } catch (IOException e) {
            Log.d("API", "Error while deleting exercise type: " + e.getMessage());
            stopSession();
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public ExerciseType saveExerciseType(ExerciseType exerciseType) {
        RequestBody reqBody = RequestBody.create(gson.toJson(exerciseType), JSON);
        String token = tokenManager.getToken();

        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/exercisetypes")
                .post(reqBody)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response res = client.newCall(req).execute()) {
            return gson.fromJson(res.body().string(), ExerciseType.class);
        } catch (IOException e) {
            Log.d("API", "Error while saving exercise type: " + e.getMessage());
            stopSession();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    @Override
    public void updateWorkout(Workout workout) {

        RequestBody reqBody = RequestBody.create(gson.toJson(workout), JSON);
        String token = tokenManager.getToken();

        Request req = new Request.Builder()
                .url(APIEndpoints.USER_URL + "/workouts/" + workout.getId())
                .put(reqBody)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response res = client.newCall(req).execute()) {
            gson.fromJson(res.body().string(), Workout.class);
        } catch (IOException e) {
            Log.d("API", "Error while updating workout: " + e.getMessage());
            stopSession();
        }
    }


    /**
     * gets motivational quote from database based on system language
     * @return a motivational
     */
    @Override
    public String getQuotes() {
        String systemLanguage = Locale.getDefault().getLanguage();
        Request req = new Request.Builder()
                .url(APIEndpoints.BASE_URL + "/api/quotes/" + systemLanguage)
                .get()
                .build();


        try (Response res = client.newCall(req).execute()) {
            String s = res.body().string();
            return s.substring(10, (s.length() - 2));
        } catch (IOException e) {
            Log.d("API", "Error while getting quotes: " + e.getMessage());
            stopSession();
            return "";
        }
    }

    /**
     * {@inheritDoc}
     * <br>This method requires authentication.
     */
    private void stopSession() {
        tokenManager.deleteToken();
        userManager.logout();
    }

}

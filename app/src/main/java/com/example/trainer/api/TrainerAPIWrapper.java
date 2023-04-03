package com.example.trainer.api;

import android.content.Context;

import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;
import com.example.trainer.util.TokenManager;

import java.util.List;

import okhttp3.OkHttpClient;

public class TrainerAPIWrapper implements AuthOperations, ExerciseTypeOperations, WorkoutOperations {

    private final OkHttpClient client;
    private final TokenManager tokenManager;

    public TrainerAPIWrapper(Context context){
        this.tokenManager = new TokenManager(context);
        this.client = new OkHttpClient();
    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        return null;
    }

    @Override
    public void deleteExerciseType(String id) {

    }

    @Override
    public void registerUser(String username, String password) {

    }

    @Override
    public void authenticateUser(String username, String password) {

    }

    @Override
    public void refreshToken() {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        return null;
    }

    @Override
    public List<Workout> getWorkouts() {
        return null;
    }

    @Override
    public void deleteWorkout(String id) {

    }
}

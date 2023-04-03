package com.example.trainer.api;

import android.content.Context;

import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.TokenManager;

import java.util.List;

public class TrainerAPIWrapper implements AuthOperations, ExerciseTypeOperations, WorkoutOperations {

    private static TrainerAPIWrapper instance;
    private final TokenManager tokenManager;

    public TrainerAPIWrapper(Context context){
        this.tokenManager = new TokenManager(context);
    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        return null;
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
    public void saveWorkout(Workout workout) {

    }
}

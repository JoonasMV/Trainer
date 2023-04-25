package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.List;
import java.util.concurrent.Future;

public interface TrainerController {

    /**
     * Starts a new workout with given name
     * @param workoutName name of workout
     */
    void startWorkout(String workoutName);

    /**
     * Cancels workout and clears shared preferences. Context is required for clearing preferences.
     * @param context App context
     */
    void cancelWorkout(Context context);

    /**
     * Saves workout
     */
    void saveWorkout();

    void changeWorkoutName(String name);

    boolean workoutActive();

    void addSet(int exercisePosition);

    void addExercise(Exercise exercise);

    void saveToPref(Context context);

    void readFromPref(Context context);

    void startWorkoutFromPreset(Workout workout);

    List<ExerciseType> getExerciseTypes();

    void deleteExerciseType(String id);

    User findUser();

    List<Workout> getPresetWorkouts();

    List<Workout> getNonPresetWorkouts();

    Future<List<Workout>> getPresetWorkoutsAsync();
    Future<List<Workout>> getNonPresetWorkoutsAsync();
    void deleteWorkout(Workout workout);

    boolean exerciseTypeExists(String name);

    void createExerciseType(ExerciseType type);

    void makePreset(Workout workout);

    Workout getWorkout();

    void setWorkout(Workout workout);

    Future<Boolean> registerUserAsync(User user);

    Future<Boolean> authenticateUserAsync(User user);

    boolean sessionValid();

    void refreshSession();

    void fetchWorkoutsAndExerciseTypesOnBackground();

    List<String> getUsernames();

    Future<List<ExerciseType>> getExerciseTypesAsync();

    void makeShared(Workout workout);

    Future<List<Workout>> getSharedWorkoutsAsync(String username);
}

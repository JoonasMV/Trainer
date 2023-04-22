package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.List;

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

    List<User> findAllUsers();

    List<Workout> getPresetWorkouts();

    List<Workout> getNonPresetWorkouts();

    void deleteWorkout(Workout workout);

    boolean exerciseTypeExists(String name);

    void createExerciseType(ExerciseType type);

    void makePreset(Workout workout);

    Workout getWorkout();

    void setWorkout(Workout workout);

    void registerUser(User user);

    void authenticateUser(User user);

    boolean sessionValid();

    void refreshSession();

    void fetchWorkoutsAndExerciseTypesOnBackground();
}

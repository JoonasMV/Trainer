package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;

import java.util.List;

public interface TrainerController {

    void startWorkout(String workoutName);

    void cancelWorkout(Context context);

    void saveWorkout();

    void changeWorkoutName(String name);

    boolean workoutActive();

    void addSet(int exercisePosition);

    void addExercise(Exercise exercise);

    void saveToPref(Context context);

    void readFromPref(Context context);

    void startWorkoutFromPreset(Workout workout);

    List<ExerciseType> getExerciseTypes();

    void deleteExerciseType(int id);

    void createUser(User user);

    User findUser();

    List<Workout> getPresetWorkouts();

    List<Workout> getNonPresetWorkouts();

    void deleteWorkout(Workout workout);

    boolean exerciseTypeExists(String type);

    void createExerciseType(ExerciseType type);

    void makePreset(Workout workout);

    Workout getWorkout();

    void setWorkout(Workout workout);
}

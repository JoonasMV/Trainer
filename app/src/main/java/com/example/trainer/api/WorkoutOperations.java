package com.example.trainer.api;

import com.example.trainer.model.Workout;

import java.util.List;

public interface WorkoutOperations {
    Workout saveWorkout(Workout workout);

    List<Workout> getWorkouts();

    void deleteWorkout(String id);

    Workout updateWorkout(Workout workout);

    List<Workout> getSharedWorkouts(String username);

}

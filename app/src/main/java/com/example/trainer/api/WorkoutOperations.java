package com.example.trainer.api;

import com.example.trainer.schemas.Workout;

import java.util.List;

public interface WorkoutOperations {
    void saveWorkout(Workout workout);

    List<Workout> getWorkouts();

    void deleteWorkout(String id);
}

package com.example.trainer.api;

import com.example.trainer.model.Workout;

import java.util.List;

/**
 * Includes all operations required for handling workouts
 */
public interface WorkoutOperations {

    /**
     * Saves workout
     * @param workout workout to be saved
     * @return Workout saved workout
     */
    Workout saveWorkout(Workout workout);

    /**
     * Gets all workouts
     * @return List<Workout> list of workouts
     */
    List<Workout> getWorkouts();

    /**
     * Deletes workout
     * @param id id of workout to be deleted
     */
    void deleteWorkout(String id);

    /**
     * Updates workout
     * @param workout workout to be updated
     */
    void updateWorkout(Workout workout);

    /**
     * Gets all shared workouts from specific user
     * @param username username of user
     * @return List<Workout> list of shared workouts
     */
    List<Workout> getSharedWorkouts(String username);
}

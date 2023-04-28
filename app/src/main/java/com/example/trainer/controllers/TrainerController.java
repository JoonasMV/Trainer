package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.List;
import java.util.concurrent.Future;

@SuppressWarnings("unused")
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
     * Saves the workout managed by the controller on background
     */
    void saveWorkout();

    /**
     * Saves a workout given as param on background
     * @param workout workout to be saved
     */
    void saveWorkout(Workout workout);

    void saveSharedWorkout(Workout workout);

    /**
     * Change the name of the current workout
     * @param name new name of the workout
     */
    void changeWorkoutName(String name);

    /**
     * Tells if a workout is currently active.
     * @return true if workout is active, false otherwise
     */
    boolean workoutActive();

    /**
     * Adds a set to the current exercise
     * @param exercisePosition position of the exercise in the list of exercises in the workout.
     */
    void addSet(int exercisePosition);

    /**
     * Adds a new exercise to the workout
     * @param exercise the exercise to be added
     */
    void addExercise(Exercise exercise);

    /**
     * Saves the workout to shared preferences. Context is required for saving preferences.
     * @param context app context
     */
    void saveToPref(Context context);

    /**
     * Reads the workout from shared preferences. Context is required for reading preferences.
     * @param context app context
     */
    void readFromPref(Context context);

    /**
     * Starts a workout from a preset. This method is synchronous, but can be called from UI thread.
     */
    void startWorkoutFromPreset(Workout workout);

    /**
     * Gets all exercise types. This method is synchronous.
     * @return a list of all exercise types
     */
    List<ExerciseType> getExerciseTypes();

    /**
     * Deletes the exercise type on background asynchronously.
     * @param id the id of the exercise type to delete
     */
    void deleteExerciseType(String id);


    /**
     * Gets the current logged in user. This method is synchronous but can be called from the UI thread.
     */
    User findUser();

    /**
     * Gets all workouts where {@code workout.preset == true}. This method is synchronous.
     * @return a list of all workouts
     */
    List<Workout> getPresetWorkouts();

    /**
     * Gets all workouts where {@code workout.preset == false}. This method is synchronous.
     */
    List<Workout> getNonPresetWorkouts();

    /**
     * Gets all workouts where {@code workout.preset == true}. This method is asynchronous.
     */
    Future<List<Workout>> getPresetWorkoutsAsync();

    /**
     * Gets all workouts where {@code workout.preset == false}. This method is asynchronous.
     */
    Future<List<Workout>> getNonPresetWorkoutsAsync();

    /**
     * Deletes the workout on background asynchronously.
     */
    void deleteWorkout(Workout workout);

    /**
     * Tells if a exercise type with a given name exists. This method is synchronous but can be called from UI thread.
     * @param name the name of the exercise type
     * @return true if the exercise type exists, false otherwise
     */
    boolean exerciseTypeExists(String name);

    /**
     * Creates a new exercise type asynchronously.
     * @param type the exercise type to create
     */
    void createExerciseType(ExerciseType type);

    /**
     * Saves the workout as a preset, this creates a new unique copy of the workout. This method is asynchronous.
     * @param workout the workout to save as a preset
     */
    void makePreset(Workout workout);

    /**
     * Gets the current workout. This method is synchronous but can be called from UI thread.
     * @return the current workout
     */
    Workout getWorkout();

    /**
     * Sets the current workout. This method is synchronous but can be called from UI thread.
     * @param workout the workout to set as current
     */
    void setWorkout(Workout workout);

    /**
     * Registers a user asynchronously.
     * @param user the user to register
     * @return a future that will be true if the user was registered successfully, false otherwise
     */
    Future<Boolean> registerUserAsync(User user);

    /**
     * Authenticates a user asynchronously.
     * @param user the user to authenticate
     * @return a future that will be true if the user was authenticated successfully, false otherwise
     */
    Future<Boolean> authenticateUserAsync(User user);

    /**
     * Checks if the session is valid synchronously. This method can be called from UI thread.
     */
    boolean sessionValid();

    /**
     * Refreshes the session by fetching a new token asynchronously.
     */
    void refreshSession();

    /**
     * Fetches all workouts and exercise types on background. This method is non-blocking.
     */
    void fetchWorkoutsAndExerciseTypesOnBackground();

    /**
     * Gets a list of all usernames. This method is blocking.
     * @return a list of all usernames
     */
    List<String> getUsernames();

    /**
     * Get exercise types asynchronously
     * @return a future that will contain a list of all exercise types, empty list if none or if request failed
     */
    Future<List<ExerciseType>> getExerciseTypesAsync();

    /**
     * Gets a list of quotes from the server
     * @return list of quotes
     */
    List<String> getQuotes();

    void makeShared(Workout workout);

    Future<List<Workout>> getSharedWorkoutsAsync(String username);

    void logOut();
}

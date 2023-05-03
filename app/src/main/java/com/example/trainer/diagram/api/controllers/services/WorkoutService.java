package com.example.trainer.diagram.api.controllers.services;

import android.util.Log;

import com.example.trainer.diagram.api.WorkoutOperations;
import com.example.trainer.diagram.api.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Workout business logic. Users workouts are cached in a list.
 * Uses WorkoutOperations to communicate with the api.
 * All of the methods that communicate with the api are synchronous except for fetchOnBackGround.
 */
public class WorkoutService {

    private List<Workout> workouts = new ArrayList<>();
    private final WorkoutOperations api;
    public WorkoutService(WorkoutOperations api){
        this.api = api;
    }

    /**
     * Saves a workout and adds it to the list of workouts if the request was successful.
     * @param workout the workout to save
     */
    public void save(Workout workout) {
        Workout saved = api.saveWorkout(workout);
        if(saved != null) {
            workouts.add(saved);
        }
    }

    /**
     * Gets all of the preset workouts from the list of workouts.
     * If the list is empty, it fetches the workouts from the api.
     * @return a list of preset workouts
     */
    public List<Workout> getPresetWorkouts() {
        if(workouts.isEmpty()){
            workouts = api.getWorkouts();
        }
        return filterPresets();
    }

    private List<Workout> filterPresets(){
        if(workouts.isEmpty()){
            return workouts;
        }
        return workouts
                .stream()
                .filter(Workout::preset)
                .collect(Collectors.toList());
    }

    /**
     * Gets all of the non preset workouts from the list of workouts.
     * If the list is empty, it fetches the workouts from the api.
     * @return a list of non preset workouts
     */
    public List<Workout> getNonPresetWorkouts() {
        if(workouts.isEmpty()){
            workouts = api.getWorkouts();
        }
        return filterNonPresets();
    }

    /**
     * Gets all of the shared workouts based on the username.
     * @param username the username of the user
     * @return a list of shared workouts
     */
    public List<Workout> getSharedWorkouts(String username){
        return api.getSharedWorkouts(username);
    }

    private List<Workout> filterNonPresets(){
        if(workouts.isEmpty()){
            return workouts;
        }
        return workouts
                .stream()
                .filter(workout -> !workout.preset())
                .collect(Collectors.toList());
    }

    /**
     * Deletes a workout from the list of workouts and from the api.
     * Workouts is deleted from the list even if the request fails.
     * @param workout the workout to delete
     */
    public void deleteWorkout(Workout workout) {
        api.deleteWorkout(workout.getId());
        removeFromList(workout.getId());
    }

    private void removeFromList(String id){
       this.workouts = workouts
               .stream()
               .filter(workout ->  !workout.getId().equals(id))
               .collect(Collectors.toList());
    }

    /**
     * Saves a workout as a preset and adds it to the list of workouts if the request was successful.
     * This creates a new workout with the same values as the workout passed in, but with preset set to true.
     * @param workout the workout to save as a preset
     */
    public void makePreset(Workout workout) {
        Workout preset = new Workout(workout);
        preset.setPreset(true);
        Workout saved = api.saveWorkout(preset);
        if(saved != null){
            workouts.add(saved);
        }
    }

    /**
     * Fetches the workouts from the api in a background thread.
     */
    public void fetchOnBackground(){
        Runnable runnable = () -> {
            Log.d("WorkoutService", "Fetching workouts in background...");
            workouts = api.getWorkouts();
            Log.d("WorkoutService", "Workouts fetched: " + workouts.size());
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Updates a workout in the list of workouts and in the api.
     * This does not create a new workout.
     * The workout is saved as shared in the list even if the request fails.
     * @param workout the workout to update
     */
    public void makeShared(Workout workout) {
        workout.setShared(true);
        api.updateWorkout(workout);
    }
}

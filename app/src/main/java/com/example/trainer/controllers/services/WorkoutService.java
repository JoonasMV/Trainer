package com.example.trainer.controllers.services;

import android.util.Log;

import com.example.trainer.api.WorkoutOperations;
import com.example.trainer.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class WorkoutService {

    private List<Workout> workouts = new ArrayList<>();
    private final WorkoutOperations api;
    public WorkoutService(WorkoutOperations api){
        this.api = api;
    }

    private final String TAG = "WorkoutService";

    public void save(Workout workout) {
        Log.d(TAG, "save: " + workout);
        Workout saved = api.saveWorkout(workout);
        if(saved != null) {
            workouts.add(saved);
        }
    }

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

    public List<Workout> getNonPresetWorkouts() {
        if(workouts.isEmpty()){
            workouts = api.getWorkouts();
        }
        return filterNonPresets();
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

    public void makePreset(Workout workout) {
        Workout preset = new Workout(workout);
        preset.setPreset(true);
        Workout saved = api.saveWorkout(preset);
        if(saved != null){
            workouts.add(saved);
        }
    }

    public void fetchOnBackground(){
        Runnable runnable = () -> {
            Log.d("WorkoutService", "Fetching workouts in background...");
            workouts = api.getWorkouts();
            Log.d("WorkoutService", "Workouts fetched: " + workouts.size());
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void makeShared(Workout workout) {
        workout.setShared(true);
        api.updateWorkout(workout);
    }
}

package com.example.trainer.controllers.services;

import com.example.trainer.api.WorkoutOperations;
import com.example.trainer.model.Workout;

import java.util.List;
import java.util.stream.Collectors;

public class WorkoutService {

    private List<Workout> workouts;
    private final WorkoutOperations api;
    public WorkoutService(WorkoutOperations api){
        this.api = api;
    }

    public void save(Workout workout) {
        Workout saved = api.saveWorkout(workout);
        workouts.add(saved);
    }

    public List<Workout> getPresetWorkouts() {
        if(workouts == null){
            workouts = api.getWorkouts();
        }
        return filterPresets();
    }

    private List<Workout> filterPresets(){
        return workouts
                .stream()
                .filter(Workout::preset)
                .collect(Collectors.toList());
    }

    public List<Workout> getNonPresetWorkouts() {
        if(workouts == null){
            workouts = api.getWorkouts();
        }
        return filterNonPresets();
    }

    private List<Workout> filterNonPresets(){
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
               .filter(workout -> workout.getId().equals(id))
               .collect(Collectors.toList());
    }

    public void makePreset(Workout workout) {
        workout.setPreset(true);
        Workout saved = api.saveWorkout(workout);
        workouts.add(saved);
    }
}

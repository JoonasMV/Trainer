package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseSet;
import com.example.trainer.model.Workout;
import com.example.trainer.util.WorkoutSerializer;

import java.util.ArrayList;
import java.util.Date;

public abstract class BaseController implements TrainerController{

    protected Workout workout;


    public static TrainerController getController(){
        return WorkoutController.getInstance();
    }

    @Override
    public void changeWorkoutName(String name){
        if(!name.isEmpty()){
            workout.setName(name);
        }
    }

    @Override
    public boolean workoutActive() {
        return this.workout != null;
    }

    @Override
    public void addSet(int exercisePosition){
        this.workout.getExercises().get(exercisePosition).getSets().add(new ExerciseSet());
    }

    @Override
    public void addExercise(Exercise exercise) {
        exercise.addSet(new ExerciseSet());
        workout.addExerciseToList(exercise);
    }

    @Override
    public void saveToPref(Context context){
        WorkoutSerializer.writeWorkoutToPref(this.workout, context);
    }

    @Override
    public void readFromPref(Context context){
        this.workout = WorkoutSerializer.readWorkoutFromPref(context);
    }

    @Override
    public void startWorkoutFromPreset(Workout workout){
        Workout copy = new Workout(workout.getName(), new Date());
        copy.setExercises(new ArrayList<>(workout.getExercises()));
        copy.setPreset(false);
        this.workout = copy;
    }

    @Override
    public Workout getWorkout() {
        return this.workout;
    }

    @Override
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void cancelWorkout(Context context) {
        this.workout = null;
        WorkoutSerializer.clearPrefs(context);
    }

    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

}

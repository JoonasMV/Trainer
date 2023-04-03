package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.WorkoutSerializer;

import java.util.ArrayList;
import java.util.Date;

public abstract class BaseController implements TrainerController{

    protected Workout workout;


    public static TrainerController getController(){
        return WorkoutManager.getInstance();
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
        if (workout == null) {
            //TODO: testing leftover
            this.workout = new Workout("Workout", new Date());
        }
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

//    private void resetIds(Workout workout){
//        List<Exercise> exercises = workout.getExList();
//        for(Exercise e : exercises){
//            e.setExerciseId(0);
//            e.setWorkoutId(0);
//            resetSetIds(e.getSetList());
//        }
//    }
//
//    private void resetSetIds(List<ExerciseSet> sets){
//        for(ExerciseSet set : sets){
//            set.setId(0);
//        }
//    }



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

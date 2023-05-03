package com.example.trainer.diagram.api.controllers;

import android.content.Context;

import com.example.trainer.diagram.api.model.Exercise;
import com.example.trainer.diagram.api.model.ExerciseSet;
import com.example.trainer.diagram.api.model.Workout;
import com.example.trainer.diagram.api.util.WorkoutSerializer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Abstract class for the TrainerController interface.
 * Implements the methods that are common to all of the controllers.
 */
@SuppressWarnings("unused")
public abstract class BaseController implements TrainerController{

    protected Workout workout;

    // For testing purposes
    private static TrainerController controller = null;

    /**
     * Sets the controller that is used in the app. This is mainly used for testing purposes.
     * @param controller the controller that is used in the app
     */
    public static void setController(TrainerController controller){
        BaseController.controller = controller;
    }

    /**
     * Returns the controller that is used in the app.
     * @return the controller that is used in the app
     */
    public static TrainerController getController(){
        return controller == null ? WorkoutController.getInstance() : controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeWorkoutName(String name){
        if(!name.isEmpty()){
            workout.setName(name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean workoutActive() {
        return this.workout != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSet(int exercisePosition){
        this.workout.getExercises().get(exercisePosition).getSets().add(new ExerciseSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExercise(Exercise exercise) {
        exercise.addSet(new ExerciseSet());
        workout.addExerciseToList(exercise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToPref(Context context){
        WorkoutSerializer.writeWorkoutToPref(this.workout, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readFromPref(Context context){
        this.workout = WorkoutSerializer.readWorkoutFromPref(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startWorkoutFromPreset(Workout workout){
        Workout copy = new Workout(workout.getName(), new Date());
        copy.setExercises(new ArrayList<>(workout.getExercises()));
        copy.setPreset(false);
        this.workout = copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Workout getWorkout() {
        return this.workout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    /**
     * {@inheritDoc}
     */
    public void cancelWorkout(Context context) {
        this.workout = null;
        WorkoutSerializer.clearPrefs(context);
    }

    /**
     * {@inheritDoc}
     */
    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

}

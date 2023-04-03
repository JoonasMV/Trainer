package com.example.trainer.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Workout implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;

    private List<Exercise> exercises;

    private String id;

    private boolean preset;


    public Workout (String name, Date workoutStarted, Date workoutEnded) {
        this.name = name;
        this.workoutStarted = workoutStarted;
        this.workoutEnded = workoutEnded;
        this.preset= false;
        this.exercises = new ArrayList<>();
    }

    public Workout (String name, Date workoutStarted) {
        this.name = name;
        this.workoutStarted = workoutStarted;
        this.preset = false;
        this.exercises = new ArrayList<>();
    }

    public Workout (String name) {
        this.name = name;
        this.preset = false;
        this.exercises = new ArrayList<>();
    }

    public Workout (String name, boolean preset) {
        this.name = name;
        this.preset = preset;
        this.exercises = new ArrayList<>();
    }

    public Workout(Workout workout){
        this.name = workout.getName();
        this.workoutStarted = workout.getWorkoutStarted();
        this.workoutEnded = workout.getWorkoutEnded();
        this.preset = workout.preset();
        this.exercises = workout.getExercises();
    }

    public void ended(){
        this.workoutEnded = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getWorkoutStarted() {
        return workoutStarted;
    }

    public void setWorkoutStarted(Date workoutStarted) {
        this.workoutStarted = workoutStarted;
    }

    public Date getWorkoutEnded() {
        return workoutEnded;
    }

    public void setWorkoutEnded(Date workoutEnded) {
        this.workoutEnded = workoutEnded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean preset() {
        return preset;
    }

    public void setPreset(boolean preset) {
        this.preset = preset;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExerciseToList(Exercise exercise){
        exercises.add(exercise);
    }

    @NonNull
    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", workoutStarted=" + workoutStarted +
                ", workoutEnded=" + workoutEnded +
                ", exList=" + exercises +
                ", id=" + id +
                ", preset=" + preset +
                '}';
    }
}

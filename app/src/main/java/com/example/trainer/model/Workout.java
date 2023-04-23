package com.example.trainer.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
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

    /**
     * If the workout is shared or not
     */
    private boolean shared;

    private String userId;

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
        this.name = workout.name;
        this.workoutStarted = workout.workoutStarted;
        this.workoutEnded = workout.workoutEnded;
        this.preset = workout.preset;
        this.exercises = workout.exercises;
        this.shared = workout.shared;
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

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
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

    public String getDuration(){
        Date start = this.getWorkoutStarted();
        Date end = this.getWorkoutEnded();
        long durationInMs = end.getTime() - start.getTime();
        Duration duration = Duration.ofMillis(durationInMs);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() - (hours * 60);
        long seconds = duration.getSeconds() - (duration.toMinutes() * 60);
        if(hours > 0){
            return String.format("%d:%d:%d", hours, minutes, seconds);
        }
        return String.format("00:%d:%d", minutes, seconds);
    }


    public String getDuration2(){
        Date start = this.getWorkoutStarted();
        Date end = this.getWorkoutEnded();
        SimpleDateFormat Format = new SimpleDateFormat("HH:mm:ss");

        Date dif = new Date(end.getTime()-start.getTime());
        String str3 = Format.format(dif);
        System.out.println(dif);
        System.out.println(str3);

        return str3;
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
                ", userId=" + userId +
                '}';
    }
}

package com.example.trainer.database.schemas;

import java.io.Serializable;

public class Exercise {

    //private int exerciseId;
    private String exerciseName;
    private int workoutId;

    public Exercise(String exerciseName) {
        //this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        //this.workoutId = workoutId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getWorkoutId() {
        return workoutId;
    }
}
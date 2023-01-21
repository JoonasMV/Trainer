package com.example.trainer.database.schemas;

import java.util.List;

public class Exercise {

    private int exerciseId;
    private String exerciseName;
    private int workoutId;

    public Exercise(String exerciseName, int exerciseId) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        //this.workoutId = workoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getWorkoutId() {
        return workoutId;
    }
}
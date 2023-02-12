package com.example.trainer.database.schemas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Exercise {

    private int exerciseId;
    private String exerciseName;
    private int workoutId = -1;

    private int typeId;

    private List<ExerciseSet> setList;


    public Exercise(String name, int workoutId, int typeId) {
        this.exerciseName = name;
        this.setList = new ArrayList<>();
        this.workoutId = workoutId;
        this.typeId = typeId;
    }

    public Exercise(String name, int exerciseId, int workoutId, int typeId) {
        this.exerciseName = name;
        this.exerciseId = exerciseId;
        this.setList = new ArrayList<>();
        this.workoutId = workoutId;
        this.typeId = typeId;
    }

    public Exercise(String name, List<ExerciseSet> setList, int workoutId, int typeId) {
        this.exerciseName = name;
        this.setList = setList;
        this.workoutId = workoutId;
        this.typeId = typeId;
    }

    public Exercise(String name,int exerciseId, List<ExerciseSet> setList, int workoutId, int typeId) {
        this.exerciseName = name;
        this.exerciseId = exerciseId;
        this.setList = setList;
        this.workoutId = workoutId;
        this.typeId = typeId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public List<ExerciseSet> getSetList() {
        return setList;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public void setSetList(List<ExerciseSet> setList) {
        this.setList = setList;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
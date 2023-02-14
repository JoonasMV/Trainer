package com.example.trainer.database.schemas;

import com.example.trainer.database.dao.ExerciseDAO;

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

    public Exercise(String name, int exerciseId, int workoutId, int typeId, List<ExerciseSet> sets) {
        this.exerciseId = exerciseId;
        this.workoutId = workoutId;
        this.exerciseName = name;
        this.typeId = typeId;
        if(sets == null) {
            this.setList = new ArrayList<>();
        } else {
            this.setList = sets;
        }
    }

    public Exercise(int exerciseTypeId){
        this.typeId = exerciseTypeId;
        ExerciseDAO dao = new ExerciseDAO();
        ExerciseType type = dao.getExerciseTypeById(exerciseTypeId);
        this.setList = new ArrayList<>();
        this.exerciseName = type.getName();
    }

    public void addSet(ExerciseSet set) {
        setList.add(set);
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
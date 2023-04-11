package com.example.trainer.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exercise implements Serializable {

    private static final long serialVersionUID = 3L;
    private String id;
    private ExerciseType exerciseType;
    private List<ExerciseSet> sets = new ArrayList<>();

    public Exercise(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    public void addSet(ExerciseSet set){
        sets.add(set);
    }

    public String getExerciseName(){
        return exerciseType.getName();
    }

    @Override
    public String toString(){
        return "Exercise " + exerciseType.getName() + " contains " + sets.size() + " sets";
    }
}
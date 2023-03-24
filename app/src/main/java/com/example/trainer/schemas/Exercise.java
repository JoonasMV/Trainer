package com.example.trainer.schemas;

import com.example.trainer.database.dao.sqlite.BetterSqliteDAOFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exercise implements Serializable {

    private static final long serialVersionUID = 3L;
    private String exerciseId;
    private String exerciseName;
    private String workoutId;

    private String typeId;

    private List<ExerciseSet> setList;

    public Exercise(String name, String exerciseId, String workoutId, String typeId, List<ExerciseSet> sets) {
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

    public Exercise(String exerciseTypeId){
        this.typeId = exerciseTypeId;
        ExerciseType type = new BetterSqliteDAOFactory().createExerciseTypeDAO().getExerciseTypeById(exerciseTypeId);
        this.setList = new ArrayList<>();
        this.exerciseName = type.getExerciseTypeName();
    }


    public void addSet(ExerciseSet set) {
        setList.add(set);
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public List<ExerciseSet> getSetList() {
        return setList;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    public void setSetList(List<ExerciseSet> setList) {
        this.setList = setList;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
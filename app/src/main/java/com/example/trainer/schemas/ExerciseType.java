package com.example.trainer.schemas;

import java.io.Serializable;

public class ExerciseType implements Serializable {

    private static final long serialVersionUID = 4L;
    private String _id;
    private String exerciseTypeName;

    public ExerciseType(String name) {
        this.exerciseTypeName = name;
    }

    public String getExerciseTypeName() {
        return exerciseTypeName;
    }

    public String get_id() {
        return _id;
    }

    public void setExerciseTypeName(String exerciseTypeName) {
        this.exerciseTypeName = exerciseTypeName;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    @Override
    public String toString() {
        return "ExerciseType{" +
                "exerciseTypeName='" + exerciseTypeName + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}

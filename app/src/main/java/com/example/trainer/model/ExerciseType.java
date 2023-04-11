package com.example.trainer.model;

import java.io.Serializable;

public class ExerciseType implements Serializable {

    public static final long serialVersionUID = 5L;
    private String id;
    private String name;

    public ExerciseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ExerciseType{" +
                "exerciseTypeName='" + name + '\'' +
                ", _id='" + id + '\'' +
                '}';
    }
}

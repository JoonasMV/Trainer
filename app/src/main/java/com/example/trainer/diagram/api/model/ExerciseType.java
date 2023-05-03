package com.example.trainer.diagram.api.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ExerciseType implements Serializable {

    public static final long serialVersionUID = 5L;
    @SuppressWarnings("unused")
    private String id;
    private final String name;

    public ExerciseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    @NonNull
    @Override
    public String toString() {
        return "ExerciseType{" +
                "exerciseTypeName='" + name + '\'' +
                ", _id='" + id + '\'' +
                '}';
    }
}

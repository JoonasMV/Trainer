package com.example.trainer.schemas;

public class ExerciseType {
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

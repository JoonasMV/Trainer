package com.example.trainer.schemas;

public class ExerciseType {
    private String name;

    private String id;

    public ExerciseType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExerciseType{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

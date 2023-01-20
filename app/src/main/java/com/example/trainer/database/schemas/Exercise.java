package com.example.trainer.database.schemas;

import java.util.List;

public class Exercise {

    private String name;

    private List<ExerciseSet> sets;

    private int id;



    public Exercise (String name) {
        this.name = name;

    }
    public Exercise (String name, int id) {
        this.id = id;
        this.name = name;

    }
    public Exercise (String name, List<ExerciseSet> sets) {
        this.sets = sets;
        this.name = name;

    }

    public Exercise (String name, List<ExerciseSet> sets, int id) {
        this.sets = sets;
        this.name = name;
        this.id = id;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.example.trainer.schemas;

import java.io.Serializable;

public class ExerciseSet implements Serializable {

    private static final long serialVersionUID = 2L;
    private double weight;

    private int reps;

    private String id;

    public ExerciseSet (double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }

    public ExerciseSet() {
        this.weight = -1;
        this.reps = -1;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int amount) {
        this.reps = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                ", weight=" + weight +
                ", amount=" + reps +
                ", id=" + id +
                '}';
    }
}

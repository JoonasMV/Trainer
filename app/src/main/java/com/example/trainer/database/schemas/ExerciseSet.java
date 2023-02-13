package com.example.trainer.database.schemas;

public class ExerciseSet {

    private double weight;

    private int reps;

    private int id;



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

    public int getAmount() {
        return reps;
    }

    public void setAmount(int amount) {
        this.reps = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

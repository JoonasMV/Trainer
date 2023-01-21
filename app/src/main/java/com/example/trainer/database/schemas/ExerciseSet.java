package com.example.trainer.database.schemas;

public class ExerciseSet {
    private String name;

    private double weight;

    private int reps;

    private int id;



    public ExerciseSet (String name, double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", amount=" + reps +
                ", id=" + id +
                '}';
    }
}

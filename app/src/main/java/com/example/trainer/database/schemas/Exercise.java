package com.example.trainer.database.schemas;

public class Exercise {

    private int setNumber;
    private double weight;
    private String name;

    public Exercise (int set,double  weight,String name) {
        this.setNumber = set;
        this.weight = weight;
        this.name = name;

    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

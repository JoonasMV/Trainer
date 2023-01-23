package com.example.trainer.database.schemas;

import java.util.Date;

public class Workout {
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;

    public Workout (String name, Date workoutStarted, Date workoutEnded) {
        this.name = name;
        this.workoutStarted = workoutStarted;
        this.workoutEnded = workoutEnded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

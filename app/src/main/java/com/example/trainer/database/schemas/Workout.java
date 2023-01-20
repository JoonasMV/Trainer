package com.example.trainer.database.schemas;

import java.util.Date;

public class Workout {
    private Date date;
    private String name;

    public Workout (Date datem, String name) {
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

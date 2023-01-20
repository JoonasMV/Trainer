package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;

public interface IworkoutDao {

    public ArrayList<Workout> getAllWorkouts();

    public Workout getWorkoutById();

    public int addWorkout();

    public int addManyWorkouts();
}

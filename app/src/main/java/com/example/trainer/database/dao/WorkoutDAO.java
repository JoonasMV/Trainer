package com.example.trainer.database.dao;

import android.content.Context;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;

public class WorkoutDAO implements IworkoutDao{
    DatabaseHelper dbConnection;

    public WorkoutDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    @Override
    public ArrayList<Workout> getAllWorkouts() {
        return null;
    }

    @Override
    public Workout getWorkoutById() {
        return null;
    }

    @Override
    public int addWorkout() {
        return 0;
    }

    @Override
    public int addManyWorkouts() {
        return 0;
    }
}

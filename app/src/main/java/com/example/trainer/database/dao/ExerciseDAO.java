package com.example.trainer.database.dao;

import android.content.Context;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ExerciseDAO implements IexerciseDao{
    DatabaseHelper dbConnection;

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    @Override
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> testi = new ArrayList<>();
        testi.add(new Exercise(1, 2, "testi"));
        testi.add(new Exercise(1, 2, "testi2"));
        testi.add(new Exercise(1, 2, "testi3"));

        return testi;
    }

    @Override
    public Exercise getExerciseById() {
        return null;
    }

    @Override
    public int addExercise() {
        return 0;
    }

    @Override
    public int addManyExercises() {
        return 0;
    }
}

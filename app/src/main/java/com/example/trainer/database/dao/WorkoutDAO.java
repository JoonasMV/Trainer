package com.example.trainer.database.dao;

import android.content.Context;

import com.example.trainer.database.DatabaseHelper;

public class WorkoutDAO {
    DatabaseHelper dbConnection;

    public WorkoutDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }
}

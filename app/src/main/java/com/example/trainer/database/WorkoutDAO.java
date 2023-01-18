package com.example.trainer.database;

import android.content.Context;

public class WorkoutDAO {
    DatabaseHelper dbConnection;

    public WorkoutDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }
}

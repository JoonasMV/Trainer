package com.example.trainer.database;

import android.content.Context;

public class ExerciseDAO {
    DatabaseHelper dbConnection;

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

}

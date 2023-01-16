package com.example.trainer.database;

import android.provider.BaseColumns;

public class ExerciseModel {

    private ExerciseModel() {}

    public static class Exercise implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EXERCISESET = "exerciseSet";
    }
}

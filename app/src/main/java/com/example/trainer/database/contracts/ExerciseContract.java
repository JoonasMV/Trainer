package com.example.trainer.database.contracts;

import android.provider.BaseColumns;

public class ExerciseContract implements BaseColumns {

    public static class ExerciseEntry {
        public static final String TABLE_EXERCISE = "exercise";

        public static final String EXERCISE_ID = "_id";

        public static final String EXERCISE_NAME = "exerciseName";


    }
}

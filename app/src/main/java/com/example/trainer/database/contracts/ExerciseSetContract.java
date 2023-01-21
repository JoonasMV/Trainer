package com.example.trainer.database.contracts;

import android.provider.BaseColumns;

public class ExerciseSetContract implements BaseColumns {

    public static class ExerciseSeEntry{
        public static final String TABLE_EXERCISESET = "exerciseSet";
        public static final String EXERCISESET_ID = "_id";
        public static final String EXERCISE_REPS = "reps";
        public static final String EXERCISE_WEIGHT = "weight";
        //public static final String EXERCISESET_NAME = "exerciseSetName";
        public static final String EXERCISE_ID = "exerciseId";
    }
}

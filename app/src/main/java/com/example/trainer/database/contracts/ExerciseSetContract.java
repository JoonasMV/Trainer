package com.example.trainer.database.contracts;

import android.provider.BaseColumns;

import com.example.trainer.database.schemas.ExerciseSet;

public class ExerciseSetContract implements BaseColumns {

    public static class ExerciseSeEntry{
        public static final String TABLE_EXERCISESET = "exerciseSet";
        public static final String EXERCISESET_ID = "_id";
        public static final String AMOUNT = "amount";
        public static final String WEIGHT = "weight";
        public static final String EXERCISESET_NAME = "exerciseSetName";
        public static final String EXERCISE_ID = "exerciseId";
    }
}

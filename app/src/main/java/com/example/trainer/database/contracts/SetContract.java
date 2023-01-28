package com.example.trainer.database.contracts;

import android.provider.BaseColumns;

public class SetContract implements BaseColumns {

    public static class ExerciseSeEntry{
        public static final String TABLE_SET = "exerciseSet";
        public static final String SET_ID = "_id";
        public static final String SET_NUMBER = "setNumber";
        public static final String SET_REPS = "reps";
        public static final String SET_WEIGHT = "weight";
        public static final String SET_NAME = "exerciseName";
    }
}
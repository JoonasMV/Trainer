package com.example.trainer.database.contracts;

import android.provider.BaseColumns;


public class ExerciseTypeContract implements BaseColumns {
    public static class ExerciseTypeEntry{
        public static final String TABLE_EXERCISETYPE = "exerciseType";

        public static final String EXERCISETYPE_ID = "_id";

        public static final String EXERCISETYPE_NAME = "exerciseTypeName";
    }
}

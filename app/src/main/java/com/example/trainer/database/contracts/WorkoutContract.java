package com.example.trainer.database.contracts;

import android.provider.BaseColumns;

public class WorkoutContract implements BaseColumns {

    public static class WorkoutEntry{
        public static final String TABLE_WORKOUT = "workout";
        public static final String WORKOUT_ID = "_id";
        public static final String WORKOUT_DATE = "date";
        public static final String WORKOUT_STARTED = "workoutStarted";
        public static final String WORKOUT_ENDED = "workoutEnded";
        public static final String WORKOUT_NAME = "workoutName";
        public static final String USER_ID = "userId";
    }
}
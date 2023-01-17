package com.example.trainer.database;

import android.provider.BaseColumns;
@Deprecated
public class DatabaseSchema {

    private DatabaseSchema() {}

    private static final String DATABASE_NAME = "trainer.db";
    private static final int DATABASE_VERSION = 1;

    public class exercise implements BaseColumns {
        public static final String TABLE_EXERCISE = "exercise";
        public static final String EXERCISE_ID = "exerciseId";
        public static final String SET_NUMBER = "setNumber";
        public static final String WEIGHT = "weight";
        public static final String EXERCISE_NAME = "exerciseName";
        public static final String WORKOUT_ID = "workoutId";

        public static final String CREATE_TABLE_EXERCISE =
                "CREATE TABLE " + TABLE_EXERCISE + " (" +
                EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SET_NUMBER + " INTEGER, " +
                WEIGHT + " REAL, " +
                EXERCISE_NAME + " TEXT, " +
                WORKOUT_ID + " INTEGER, " +
                "FOREIGN KEY(" + WORKOUT_ID + ") REFERENCES workout(workoutId));";
    }

    public class workout implements BaseColumns {
        public static final String TABLE_WORKOUT = "workout";
        public static final String WORKOUT_ID = "workoutId";
        public static final String DATE = "date";
        public static final String WORKOUT_NAME = "workoutName";
        public static final String USER_ID = "userId";

        public static final String CREATE_TABLE_WORKOUT =
                "CREATE TABLE " + TABLE_WORKOUT + " (" +
                WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATE + " TEXT, " +
                WORKOUT_NAME + " TEXT, " +
                USER_ID + " INTEGER,  " +
                "FOREIGN KEY("+ USER_ID +") REFERENCES user(userId));";
    }

    public class user implements BaseColumns {
        public static final String TABLE_USER = "user";
        public static final String USER_ID = "userId";
        public static final String USERNAME = "username";

        public static final String CREATE_USER_TABLE =
                "CREATE TABLE " + TABLE_USER + " ("+
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT);";
    }
}

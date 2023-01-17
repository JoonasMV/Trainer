package com.example.trainer.database;

public class WorkoutDbHelper {
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
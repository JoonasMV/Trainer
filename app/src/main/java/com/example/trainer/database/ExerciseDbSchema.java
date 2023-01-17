package com.example.trainer.database;

public class ExerciseDbSchema {
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

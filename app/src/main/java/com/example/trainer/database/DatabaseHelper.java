package com.example.trainer.database;


import com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry;
import com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry;
import com.example.trainer.database.contracts.SetContract.ExerciseSetEntry;
import com.example.trainer.database.contracts.UserContract.UserEntry;
import com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.example.trainer.database.contracts.UserContract;
;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "trainer.db";
    private static int DATABASE_VERSION = 1;
    private static DatabaseHelper dbConnection;

    private String[] basicExercises = {
            "squat", "front squat", "bench press", "incline bench press",
            "dumbbell press", "deadlift", "romanian deadlift", "barbell row",
            "overhead press", "barbell curl", "dumbbell curl", "tricep extension"
    };

    public static void initialize(Context context){
        dbConnection = new DatabaseHelper(context.getApplicationContext());
    }

    public static DatabaseHelper getInstance() {
        return dbConnection;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + UserEntry.TABLE_USER + " (" +
                        UserContract.UserEntry.USER_ID + " TEXT PRIMARY KEY, " +
                        UserContract.UserEntry.USERNAME + " TEXT);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseEntry.TABLE_EXERCISE + " (" +
                        ExerciseEntry.EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ExerciseEntry.WORKOUT_ID + " INTEGER," +
                        ExerciseEntry.EXERCISE_TYPEID + " INTEGER," +
                        "FOREIGN KEY(" + ExerciseEntry.EXERCISE_TYPEID + ") REFERENCES " + ExerciseTypeEntry.TABLE_EXERCISETYPE + "(" + ExerciseTypeEntry.EXERCISETYPE_ID + ")," +
                        "FOREIGN KEY(" + ExerciseEntry.WORKOUT_ID + ") REFERENCES " + WorkoutEntry.TABLE_WORKOUT + "(" + WorkoutEntry.WORKOUT_ID + "));"

        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseTypeEntry.TABLE_EXERCISETYPE + " (" +
                        ExerciseTypeEntry.EXERCISETYPE_ID + " TEXT PRIMARY KEY, " +
                        ExerciseTypeEntry.EXERCISETYPE_NAME + " TEXT" + ");"

        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseSetEntry.TABLE_SET + " (" +
                        ExerciseSetEntry.SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ExerciseSetEntry.SET_REPS + " INTEGER," +
                        ExerciseSetEntry.SET_WEIGHT + " DOUBLE," +
                        ExerciseSetEntry.EXERCISE_ID + " INTEGER," +
                        "FOREIGN KEY(" + ExerciseSetEntry.EXERCISE_ID + ") REFERENCES " + ExerciseEntry.TABLE_EXERCISE + "(" + ExerciseEntry.EXERCISE_ID + "));"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + WorkoutEntry.TABLE_WORKOUT + " (" +
                        WorkoutEntry.WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WorkoutEntry.WORKOUT_NAME + " TEXT, " +
                        WorkoutEntry.WORKOUT_STARTED + " TEXT, " +
                        WorkoutEntry.WORKOUT_ENDED + " TEXT, " +
                        WorkoutEntry.USER_ID + " INTEGER,  " +
                        WorkoutEntry.PRESET + " INTEGER, " +
                        "FOREIGN KEY(" + UserContract.UserEntry.USER_ID + ") REFERENCES " + UserContract.UserEntry.TABLE_USER + "(" + UserContract.UserEntry.USER_ID + "));"
        );

        for (String exercise: basicExercises) {
            sqLiteDatabase.execSQL("INSERT INTO " + ExerciseTypeEntry.TABLE_EXERCISETYPE + " (" + ExerciseTypeEntry.EXERCISETYPE_NAME + ") VALUES (\"" + exercise + "\");");
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.example.trainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "trainer.db";
    private static int DATABASE_VERSION = 1;
    private static DatabaseHelper dbConnection;
    private Context context;

    public static DatabaseHelper getInstance(Context context) {
        if (dbConnection == null) {
            dbConnection = new DatabaseHelper(context);
        }
        return dbConnection;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + UserContract.UserEntry.TABLE_USER + " (" +
                        UserContract.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        UserContract.UserEntry.USERNAME + " TEXT);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_EXERCISE + " (" +
                        ExerciseContract.ExerciseEntry.EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ExerciseContract.ExerciseEntry.SET_NUMBER + " INTEGER, " +
                        ExerciseContract.ExerciseEntry.WEIGHT + " REAL, " +
                        ExerciseContract.ExerciseEntry.EXERCISE_NAME + " TEXT, " +
                        ExerciseContract.ExerciseEntry.WORKOUT_ID + " INTEGER, " +
                        "FOREIGN KEY(" + ExerciseContract.ExerciseEntry.WORKOUT_ID + ") REFERENCES " + WorkoutContract.WorkoutEntry.TABLE_WORKOUT + "(" + WorkoutContract.WorkoutEntry.WORKOUT_ID + "));"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + WorkoutContract.WorkoutEntry.TABLE_WORKOUT + " (" +
                        WorkoutContract.WorkoutEntry.WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WorkoutContract.WorkoutEntry.DATE + " TEXT, " +
                        WorkoutContract.WorkoutEntry.WORKOUT_NAME + " TEXT, " +
                        UserContract.UserEntry.USER_ID + " INTEGER,  " +
                        "FOREIGN KEY(" + UserContract.UserEntry.USER_ID + ") REFERENCES " + UserContract.UserEntry.TABLE_USER + "(" + UserContract.UserEntry.USER_ID + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
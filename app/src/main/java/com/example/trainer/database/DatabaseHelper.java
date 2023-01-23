package com.example.trainer.database;

import com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry;
import com.example.trainer.database.contracts.SetContract.ExerciseSeEntry;
import com.example.trainer.database.contracts.UserContract.UserEntry;
import com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.trainer.database.contracts.UserContract;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "trainer.db";
    private static int DATABASE_VERSION = 1;
    private static DatabaseHelper dbConnection;
    private Context context;

    private String[] basicExercises = { "squat", "bench", "deadlift" };

    public static DatabaseHelper getInstance(Context context) {
        if (dbConnection == null) {
            dbConnection = new DatabaseHelper(context.getApplicationContext());
            dbConnection.getReadableDatabase();
            dbConnection.close();
        }
        return dbConnection;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + UserEntry.TABLE_USER + " (" +
                        UserContract.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        UserContract.UserEntry.USERNAME + " TEXT);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseEntry.TABLE_EXERCISE + " (" +
                        ExerciseEntry.EXERCISE_NAME + " TEXT PRIMARY KEY);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseSeEntry.TABLE_SET + " (" +
                        ExerciseSeEntry.SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ExerciseSeEntry.SET_REPS + " INTEGER," +
                        ExerciseSeEntry.SET_NUMBER + " INTEGER," +
                        ExerciseSeEntry.SET_WEIGHT + " DOUBLE," +
                        ExerciseSeEntry.SET_NAME + " INTEGER," +
                        "FOREIGN KEY(" + ExerciseSeEntry.SET_NAME + ") REFERENCES " + ExerciseEntry.TABLE_EXERCISE + "(" + ExerciseEntry.EXERCISE_NAME + "));"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + WorkoutEntry.TABLE_WORKOUT + " (" +
                        WorkoutEntry.WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WorkoutEntry.WORKOUT_NAME + " TEXT, " +
                        WorkoutEntry.WORKOUT_STARTED + " TEXT, " +
                        WorkoutEntry.WORKOUT_ENDED + " TEXT, " +
                        WorkoutEntry.USER_ID + " INTEGER,  " +
                        "FOREIGN KEY(" + UserContract.UserEntry.USER_ID + ") REFERENCES " + UserContract.UserEntry.TABLE_USER + "(" + UserContract.UserEntry.USER_ID + "));"
        );

        for (String exercise: basicExercises) {
            sqLiteDatabase.execSQL("INSERT INTO " + ExerciseEntry.TABLE_EXERCISE + " (" + ExerciseEntry.EXERCISE_NAME + ") VALUES (\"" + exercise + "\");");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}

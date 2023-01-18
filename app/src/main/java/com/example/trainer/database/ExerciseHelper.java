package com.example.trainer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.trainer.database.ExerciseContract.ExerciseEntry;
import com.example.trainer.database.WorkoutContract.WorkoutEntry;
import com.example.trainer.database.DatabaseContract.DatabaseArgs;

import androidx.annotation.Nullable;

public class ExerciseHelper extends SQLiteOpenHelper {

    public ExerciseHelper(@Nullable Context context) {
        super(context, DatabaseArgs.DATABASE_NAME, null, DatabaseArgs.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_EXERCISE =
            "CREATE TABLE " + ExerciseEntry.TABLE_EXERCISE + " (" +
                    ExerciseEntry.EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ExerciseEntry.SET_NUMBER + " INTEGER, " +
                    ExerciseEntry.WEIGHT + " REAL, " +
                    ExerciseEntry.EXERCISE_NAME + " TEXT, " +
                    ExerciseEntry.WORKOUT_ID + " INTEGER, " +
                    "FOREIGN KEY(" + ExerciseEntry.WORKOUT_ID + ") REFERENCES " + WorkoutEntry.TABLE_WORKOUT + "(" + WorkoutEntry.WORKOUT_ID+");";

        sqLiteDatabase.execSQL(CREATE_TABLE_EXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

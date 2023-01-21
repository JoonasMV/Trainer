package com.example.trainer.database;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.EXERCISE_NAME;
import static com.example.trainer.database.contracts.ExerciseSetContract.ExerciseSeEntry.EXERCISE_REPS;
import static com.example.trainer.database.contracts.ExerciseSetContract.ExerciseSeEntry.EXERCISESET_ID;
import static com.example.trainer.database.contracts.ExerciseSetContract.ExerciseSeEntry.TABLE_EXERCISESET;
import static com.example.trainer.database.contracts.ExerciseSetContract.ExerciseSeEntry.EXERCISE_WEIGHT;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.trainer.database.contracts.ExerciseContract;
import com.example.trainer.database.contracts.ExerciseSetContract;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.database.contracts.WorkoutContract;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "trainer.db";
    private static int DATABASE_VERSION = 1;
    private static DatabaseHelper dbConnection;
    private Context context;

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
                "CREATE TABLE " + UserContract.UserEntry.TABLE_USER + " (" +
                        UserContract.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        UserContract.UserEntry.USERNAME + " TEXT);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ExerciseContract.ExerciseEntry.TABLE_EXERCISE + " (" +
                        ExerciseContract.ExerciseEntry.EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        EXERCISE_NAME + " TEXT" + ");"
                        //ExerciseContract.ExerciseEntry.WORKOUT_ID + " INTEGER," +
                        //"FOREIGN KEY(" + ExerciseContract.ExerciseEntry.WORKOUT_ID + ") REFERENCES " + WorkoutContract.WorkoutEntry.TABLE_WORKOUT + "(" + WorkoutContract.WorkoutEntry.WORKOUT_ID + "));"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_EXERCISESET + " (" +
                        EXERCISESET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        EXERCISE_REPS + " INTEGER," +
                        EXERCISE_WEIGHT + " DOUBLE," +
                        //EXERCISESET_NAME + " TEXT," +
                        ExerciseSetContract.ExerciseSeEntry.EXERCISE_ID + " INTEGER," +
                        "FOREIGN KEY(" + ExerciseSetContract.ExerciseSeEntry.EXERCISE_ID + ") REFERENCES " + ExerciseContract.ExerciseEntry.TABLE_EXERCISE + "(" + ExerciseContract.ExerciseEntry.EXERCISE_ID + "));"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + WorkoutContract.WorkoutEntry.TABLE_WORKOUT + " (" +
                        WorkoutContract.WorkoutEntry.WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WorkoutContract.WorkoutEntry.DATE + " TEXT, " +
                        WorkoutContract.WorkoutEntry.WORKOUT_NAME + " TEXT, " +
                        WorkoutContract.WorkoutEntry.USER_ID + " INTEGER,  " +
                        "FOREIGN KEY(" + UserContract.UserEntry.USER_ID + ") REFERENCES " + UserContract.UserEntry.TABLE_USER + "(" + UserContract.UserEntry.USER_ID + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}

package com.example.trainer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.trainer.database.DatabaseContract.DatabaseArgs;
import com.example.trainer.database.UserContract.UserEntry;
import com.example.trainer.database.WorkoutContract.WorkoutEntry;

import androidx.annotation.Nullable;

public class WorkoutHelper extends SQLiteOpenHelper {
    public WorkoutHelper(@Nullable Context context) {
        super(context, DatabaseArgs.DATABASE_NAME, null, DatabaseArgs.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_WORKOUT =
            "CREATE TABLE " + WorkoutEntry.TABLE_WORKOUT + " (" +
                WorkoutEntry.WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkoutEntry.DATE + " TEXT, " +
                WorkoutEntry.WORKOUT_NAME + " TEXT, " +
                UserContract.UserEntry.USER_ID + " INTEGER,  " +
                "FOREIGN KEY("+ UserEntry.USER_ID +") REFERENCES "+ UserEntry.TABLE_USER +"(" + UserEntry.USER_ID + "));";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

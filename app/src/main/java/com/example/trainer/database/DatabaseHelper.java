package com.example.trainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trainer.db";
    private static final int DATABASE_VERSION = 1;

    //private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.context = context;
        db.execSQL(ExerciseDbHelper.CREATE_TABLE_EXERCISE);
        db.execSQL(UserDbHelper.CREATE_USER_TABLE);
        db.execSQL(WorkoutDbHelper.CREATE_TABLE_WORKOUT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


    public boolean insertUserName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserDbHelper.USERNAME, username);
        long success = db.insert(UserDbHelper.TABLE_USER, null, cv);

        if (success == -1) return false;
        return true;
    }

    public boolean addOne() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        return false;
    }
}

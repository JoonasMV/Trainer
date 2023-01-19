package com.example.trainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper implements IexerciseDao, IuserDao{

    private static final String DATABASE_NAME = "trainer.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this.context = context;
        db.execSQL(Exercise.CREATE_TABLE_EXERCISE);
        db.execSQL(User.CREATE_USER_TABLE);
        db.execSQL(Workout.CREATE_TABLE_WORKOUT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


    public boolean insertUserName(String username) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(User.USERNAME, username);
        long success = db.insert(User.TABLE_USER, null, cv);

        if (success == -1) return false;
        return true;
    }


    public String getUser() {
        return "Mikko :)";
    }
        /*
        db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE_USER + ";", null);

        //TODO: testausta varten, korjataan soon(tm)
        try {
            cursor.moveToFirst();
            String usernameFromDb = cursor.getString(cursor.getColumnIndexOrThrow(User.USERNAME));
            System.out.println(usernameFromDb);
            cursor.close();
            return usernameFromDb;
        } catch (Exception e) {
            cursor.close();
            System.out.println("hello");
            return null;
        }


    }*/

    public String[] getAllExercises() {
        String[] array = {"testi", "testi2", "testi3"};
        return array;
    }

    @Override
    public Exercise getExerciseById() {
        return null;
    }

    @Override
    public int addExercise() {
        return 0;
    }

    @Override
    public int addManyExercises() {
        return 0;
    }

    @Override
    public User addUser() {
        return null;
    }


}

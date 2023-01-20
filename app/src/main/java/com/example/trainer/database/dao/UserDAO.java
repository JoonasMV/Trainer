package com.example.trainer.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.database.schemas.User;

public class UserDAO implements IuserDao{
    DatabaseHelper dbConnection;

    public UserDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    public boolean createUser(String username) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.USERNAME, username);
        long success = db.insert(UserContract.UserEntry.TABLE_USER, null, cv);
        db.close();

        if (success == -1) return false;
        return true;
    }

    /*
    public String readUser() {
        SQLiteDatabase db = dbConnection.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_USER + ";", null);

        //TODO: testausta varten, korjataan soon(tm)
        try {
            cursor.moveToFirst();
            String usernameFromDb = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.USERNAME));

            cursor.close();
            db.close();
            return usernameFromDb;
        } catch (Exception e) {
            cursor.close();
            db.close();
            System.out.println("Error in getUser()");
            return null;
        }
    }

     */

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public User getUser(int id) {
        return null;
    }
}

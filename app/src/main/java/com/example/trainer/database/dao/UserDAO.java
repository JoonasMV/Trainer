package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.database.schemas.User;
import com.example.trainer.database.contracts.UserContract.UserEntry;

public class UserDAO {
    DatabaseHelper dbConnection;

    public UserDAO() {
        dbConnection = DatabaseHelper.getInstance();
    }

    public boolean createUser(User newUser) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.USERNAME, newUser.getUsername());
        long success = db.insert(UserContract.UserEntry.TABLE_USER, null, cv);
        db.close();

        if (success == -1) return false;
        return true;
    }

    public User getUser() {
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursor = db.query(UserEntry.TABLE_USER, null, null, null, null, null, null);
        String usernameFromDb = null;

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    usernameFromDb = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.USERNAME));
                    if (usernameFromDb != null) return new User(usernameFromDb);
                }
            }
            return null;
        } catch (Exception e) {
            cursor.close();
            db.close();
            System.out.println("Error in getUser()");
            return null;
        } finally {
            cursor.close();
            db.close();
        }
    }



    public int addUser(User user) {
        return 0;
    }

    public User getUser(int id) {
        return null;
    }
}

package com.example.trainer.mainActivity.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.mainActivity.dao.framework.IUserDAO;
import com.example.trainer.schemas.User;
import com.example.trainer.database.contracts.UserContract.UserEntry;

public class UserDAO implements IUserDAO {
    DatabaseHelper dbConnection;

    public UserDAO() {
        dbConnection = DatabaseHelper.getInstance();
    }

    @Override
    public void createUser(User newUser) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.USERNAME, newUser.getUsername());

        db.insert(UserContract.UserEntry.TABLE_USER, null, cv);
        db.close();
    }

    @Override
    public User getUser() {
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        Cursor cursor = db.query(UserEntry.TABLE_USER, null, null, null, null, null, null);
        String usernameFromDb = null;

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
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
}

package com.example.trainer.database.legacyDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.database.contracts.UserContract.UserEntry;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.schemas.User;
import com.example.trainer.serverConnector.Server;

public class UserDAO implements IUserDAO {
    DatabaseHelper dbConnection;
    Server server = Server.getInstance();

    public UserDAO() {
        dbConnection = DatabaseHelper.getInstance();
    }

    @Override
    public void createUser(User newUser) {
        System.out.println("userDao");
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();

        User createdUser = server.user().save(newUser);

        cv.put(UserContract.UserEntry.USERNAME, createdUser.getUsername());
        cv.put(UserEntry.USER_ID, createdUser.getId());
        long success = db.insert(UserContract.UserEntry.TABLE_USER, null, cv);
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

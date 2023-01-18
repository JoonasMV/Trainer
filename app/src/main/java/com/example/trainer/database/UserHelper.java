package com.example.trainer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainer.database.UserContract.UserEntry;
import com.example.trainer.database.DatabaseContract.DatabaseArgs;

import androidx.annotation.Nullable;

public class UserHelper extends SQLiteOpenHelper {

    public UserHelper(@Nullable Context context) {
        super(context, DatabaseArgs.DATABASE_NAME, null, DatabaseArgs.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USER =
            "CREATE TABLE " + UserEntry.TABLE_USER + " (" +
                UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserEntry.USERNAME + " TEXT);";

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean saveUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserEntry.USERNAME, username);
        long success = db.insert(UserEntry.TABLE_USER, null, cv);
        db.close();

        if (success == -1) return false;
        return true;
    }

    public String getUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserEntry.TABLE_USER + ";", null);

        //TODO: testausta varten, korjataan soon(tm)
        try {
            cursor.moveToFirst();
            String usernameFromDb = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.USERNAME));
            System.out.println(usernameFromDb);
            cursor.close();
            return usernameFromDb;
        } catch (Exception e) {
            cursor.close();
            System.out.println("hello");
            return null;
        }
    }
}

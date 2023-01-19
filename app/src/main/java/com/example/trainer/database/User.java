package com.example.trainer.database;

public class User {

    public static final String TABLE_USER = "user";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + " ("+
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USERNAME + " TEXT);";
}

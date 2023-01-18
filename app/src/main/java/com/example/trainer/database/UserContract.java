package com.example.trainer.database;

import android.provider.BaseColumns;

public class UserContract implements BaseColumns {

    public static class UserEntry {
        public static final String TABLE_USER = "user";
        public static final String USER_ID = "_id";
        public static final String USERNAME = "username";
    }
}

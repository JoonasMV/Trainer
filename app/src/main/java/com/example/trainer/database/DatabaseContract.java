package com.example.trainer.database;

import android.provider.BaseColumns;

public final class DatabaseContract implements BaseColumns {

    private DatabaseContract() {}
    public final static class DatabaseArgs {
        public final static String DATABASE_NAME = "trainer.db";
        public final static int DATABASE_VERSION = 1;
    }
}

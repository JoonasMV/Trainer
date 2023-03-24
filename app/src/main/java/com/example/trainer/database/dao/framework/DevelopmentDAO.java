package com.example.trainer.database.dao.framework;

public abstract class DevelopmentDAO {

    public static DevelopmentDAO newInstance(){
        return new SqliteDevDAO();
    }

    public abstract void clearDatabase();
}

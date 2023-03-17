package com.example.trainer.mainActivity.dao.framework;

public abstract class DevelopmentDAO {

    public static DevelopmentDAO newInstance(){
        return new SqliteDevDAO();
    }
    public abstract void deleteAllExercises();
    public abstract void deleteAllWorkouts();
    public abstract void deleteAllExerciseTypes();
    public abstract void deleteAllUsers();
    public abstract void deleteAllSets();
}

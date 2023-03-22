package com.example.trainer.database.dao.framework;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.SetDAO;
import com.example.trainer.database.dao.WorkoutDAO;

public class SqliteDevDAO extends DevelopmentDAO {

    private final ExerciseDAO exerciseDAO = new ExerciseDAO();
    private final WorkoutDAO workoutDAO = new WorkoutDAO();
    private final SetDAO setDAO = new SetDAO();
//    private UserDAO userDAO = new UserDAO(); need to add method for deleting all users

    protected SqliteDevDAO(){}

    @Override
    public void deleteAllExercises() {
        exerciseDAO.deleteAll();
    }

    @Override
    public void deleteAllWorkouts() {
        workoutDAO.deleteAllWorkouts();
    }

    @Override
    public void deleteAllExerciseTypes() {
        exerciseDAO.deleteAllExerciseTypes();
    }

    @Override
    public void deleteAllUsers() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public void deleteAllSets() {
       setDAO.deleteAllSets();
    }
}

package com.example.trainer.database.dao.framework;

public interface DAOFactory {
    IWorkoutDAO createWorkoutDAO();

    IExerciseDAO createExerciseDAO();

    ISetDAO createSetDAO();

    IUserDAO createUserDAO();

    IExerciseTypeDAO createExerciseTypeDAO();
}

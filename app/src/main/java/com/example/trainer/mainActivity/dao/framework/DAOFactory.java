package com.example.trainer.mainActivity.dao.framework;

public interface DAOFactory {
    IWorkoutDAO createWorkoutDAO();

    IExerciseDAO createExerciseDAO();

    ISetDAO createSetDAO();

    IUserDAO createUserDAO();

    IExerciseTypeDAO createExerciseTypeDAO();
}

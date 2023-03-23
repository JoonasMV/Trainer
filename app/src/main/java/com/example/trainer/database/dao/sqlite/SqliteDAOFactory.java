package com.example.trainer.database.dao.sqlite;

import com.example.trainer.database.dao.framework.DAOFactory;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.ISetDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.database.legacyDAO.ExerciseDAO;
import com.example.trainer.database.legacyDAO.SetDAO;
import com.example.trainer.database.legacyDAO.UserDAO;
import com.example.trainer.database.legacyDAO.WorkoutDAO;

public class SqliteDAOFactory implements DAOFactory {

    public SqliteDAOFactory(){

    }

    @Override
    public IWorkoutDAO createWorkoutDAO() {
       return new WorkoutDAO();
    }

    @Override
    public IExerciseDAO createExerciseDAO() {
        return new ExerciseDAO();
    }

    @Override
    public ISetDAO createSetDAO() {
        return new SetDAO();
    }

    @Override
    public IUserDAO createUserDAO() {
        return new UserDAO();
    }

    @Override
    public IExerciseTypeDAO createExerciseTypeDAO() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }
}

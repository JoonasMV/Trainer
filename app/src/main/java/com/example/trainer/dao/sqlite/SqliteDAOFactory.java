package com.example.trainer.dao.sqlite;

import com.example.trainer.dao.framework.DAOFactory;
import com.example.trainer.dao.framework.IExerciseDAO;
import com.example.trainer.dao.framework.ISetDAO;
import com.example.trainer.dao.framework.IUserDAO;
import com.example.trainer.dao.framework.IWorkoutDAO;
import com.example.trainer.dao.ExerciseDAO;
import com.example.trainer.dao.framework.IExerciseTypeDAO;
import com.example.trainer.dao.SetDAO;
import com.example.trainer.dao.UserDAO;
import com.example.trainer.dao.WorkoutDAO;

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

package com.example.trainer.mainActivity.dao.sqlite;

import com.example.trainer.mainActivity.dao.framework.DAOFactory;
import com.example.trainer.mainActivity.dao.ExerciseDAO;
import com.example.trainer.mainActivity.dao.framework.IExerciseDAO;
import com.example.trainer.mainActivity.dao.framework.IExerciseTypeDAO;
import com.example.trainer.mainActivity.dao.framework.ISetDAO;
import com.example.trainer.mainActivity.dao.framework.IUserDAO;
import com.example.trainer.mainActivity.dao.framework.IWorkoutDAO;
import com.example.trainer.mainActivity.dao.SetDAO;
import com.example.trainer.mainActivity.dao.UserDAO;
import com.example.trainer.mainActivity.dao.WorkoutDAO;

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

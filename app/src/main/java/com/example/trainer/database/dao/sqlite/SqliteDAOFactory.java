package com.example.trainer.database.dao.sqlite;

import com.example.trainer.database.contracts.ExerciseContract;
import com.example.trainer.database.contracts.WorkoutContract;
import com.example.trainer.database.dao.entityCreators.EntityCreator;
import com.example.trainer.database.dao.entityCreators.ExerciseSetEntityCreator;
import com.example.trainer.database.dao.entityCreators.WorkoutEntityCreator;
import com.example.trainer.database.dao.framework.DAOFactory;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.ISetDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.schemas.Workout;

public class SqliteDAOFactory implements DAOFactory {

    public SqliteDAOFactory(){

    }

    @Override
    public IWorkoutDAO createWorkoutDAO() {
       return new SqliteWorkoutDAO(new WorkoutEntityCreator(new BetterSqliteDAOFactory().createExerciseDAO()),
               WorkoutContract.WorkoutEntry.TABLE_WORKOUT);
    }

    @Override
    public IExerciseDAO createExerciseDAO() {
        return new SqliteExerciseDAO();
    }

    @Override
    public ISetDAO createSetDAO() {
        return new SqliteSetDAO(new ExerciseSetEntityCreator(),
                ExerciseContract.ExerciseEntry.TABLE_EXERCISE);
    }

    @Override
    public IUserDAO createUserDAO() {
        return new SqliteUserDao();
    }

    @Override
    public IExerciseTypeDAO createExerciseTypeDAO() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }
}

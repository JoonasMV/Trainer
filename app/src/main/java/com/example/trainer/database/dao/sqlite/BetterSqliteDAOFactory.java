package com.example.trainer.database.dao.sqlite;

import com.example.trainer.database.contracts.ExerciseTypeContract;
import com.example.trainer.database.contracts.SetContract;
import com.example.trainer.database.contracts.WorkoutContract;
import com.example.trainer.database.dao.entityCreators.EntityCreator;
import com.example.trainer.database.dao.entityCreators.ExerciseSetEntityCreator;
import com.example.trainer.database.dao.entityCreators.ExerciseTypeEntityCreator;
import com.example.trainer.database.dao.entityCreators.WorkoutEntityCreator;
import com.example.trainer.database.dao.framework.DAOFactory;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.ISetDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;

public class BetterSqliteDAOFactory implements DAOFactory {

    public BetterSqliteDAOFactory(){

    }
    @Override
    public IWorkoutDAO createWorkoutDAO() {
        String table = WorkoutContract.WorkoutEntry.TABLE_WORKOUT;
        IExerciseDAO exerciseDAO = new SqliteExerciseDAO();
        EntityCreator<Workout> creator = new WorkoutEntityCreator(exerciseDAO);
        return new SqliteWorkoutDAO(creator, table);
    }

    @Override
    public IExerciseDAO createExerciseDAO() {
        return new SqliteExerciseDAO();
    }

    @Override
    public ISetDAO createSetDAO() {
        String table = SetContract.ExerciseSetEntry.TABLE_SET;
        EntityCreator<ExerciseSet> creator = new ExerciseSetEntityCreator();
        return new SqliteSetDAO(creator, table);
    }

    @Override
    public IUserDAO createUserDAO() {
        return new SqliteUserDao();
    }

    @Override
    public IExerciseTypeDAO createExerciseTypeDAO() {
        String table = ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;
        EntityCreator<ExerciseType> creator = new ExerciseTypeEntityCreator();
        return new SqliteExerciseTypeDAO(creator, table);
    }
}

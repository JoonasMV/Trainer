package com.example.trainer.database.dao.sqlite;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.WORKOUT_ID;
import static com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;
import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.TABLE_SET;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.contracts.ExerciseContract;
import com.example.trainer.database.dao.entityCreators.ExerciseEntityCreator;
import com.example.trainer.database.dao.entityCreators.ExerciseSetEntityCreator;
import com.example.trainer.database.dao.framework.DAOBase;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.ISetDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;

import java.util.ArrayList;
import java.util.List;

public class SqliteExerciseDAO extends DAOBase<Exercise> implements IExerciseDAO {

    private final ISetDAO setDAO;


    private final String columns = "exerciseType._id AS typeId, exerciseType.exerciseTypeName AS name, exercise.workoutId, exercise._id AS id";
    private final String whereQ = "SELECT " + columns + " FROM " + TABLE_EXERCISETYPE + " INNER JOIN " + TABLE_EXERCISE + " ON exercise.exerciseTypeId = exerciseType._id WHERE ";
    public SqliteExerciseDAO(){
        super(new ExerciseEntityCreator(new SqliteSetDAO(new ExerciseSetEntityCreator(), TABLE_SET)), ExerciseContract.ExerciseEntry.TABLE_EXERCISE);
        this.setDAO = new BetterSqliteDAOFactory().createSetDAO();
    }
    @Override
    public String save(Exercise exercise) {
        List<ExerciseSet> sets = exercise.getSetList();
        exercise.setExerciseId(0);
        ContentValues cv = createCV(exercise);
        saveToDb(cv);
        String id = getIdOfLastInsertedRow();
        setDAO.saveMany(sets, id);
        return id;
    }

    private ContentValues createCV(Exercise exercise){
            ContentValues cv = new ContentValues();

        cv.put(ExerciseContract.ExerciseEntry.EXERCISE_TYPEID, exercise.getTypeId());
        cv.put(ExerciseContract.ExerciseEntry.WORKOUT_ID, exercise.getWorkoutId());
        return cv;
    }

    @Override
    public Exercise getById(String id) {
        SQLiteDatabase db = readableDB();
        String clause = "_id=?";
        Cursor cursor = db.rawQuery(String.format("%s%s", whereQ, clause), createArgs(id));
        if(cursor.moveToFirst()){
           return readRow(cursor);
        }
       return null;
    }


    @Override
    public void saveMany(List<Exercise> exercises) {
        for (Exercise e : exercises){
            save(e);
        }
    }

    @Override
    public void delete(Exercise exercise) {
        String id = exercise.getExerciseId();
        removeFromDb("_id=?", createArgs(id));
        setDAO.deleteAllSetsFromExercise(id);
    }


    @Override
    public void update(Exercise exercise) {
        ContentValues cv = createCV(exercise);
        List<ExerciseSet> sets = exercise.getSetList();
        updateInDb(cv, "_id=?", createArgs(exercise.getExerciseId()));
        for(ExerciseSet set : sets){
            setDAO.update(set,exercise.getExerciseId());
        }
    }

    @Override
    public List<Exercise> getByWorkoutId(String id) {
        SQLiteDatabase db = readableDB();
        String clause = String.format("%s=?", WORKOUT_ID);
        //TODO: args param
        Cursor cursor = db.rawQuery(String.format("%s%s", whereQ, clause), new String[]{ id });
        List<Exercise> results = new ArrayList<>();
        while(cursor.moveToNext()){
            Exercise e = readRow(cursor);
            results.add(e);
        }
        return results;
    }
}

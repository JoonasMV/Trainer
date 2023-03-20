package com.example.trainer.mainActivity.dao.sqlite;

import android.content.ContentValues;

import com.example.trainer.database.contracts.SetContract;
import com.example.trainer.mainActivity.dao.entityCreators.EntityCreator;
import com.example.trainer.mainActivity.dao.framework.DAOBase;
import com.example.trainer.mainActivity.dao.framework.ISetDAO;
import com.example.trainer.schemas.ExerciseSet;

import java.util.List;

public class SqliteSetDAO extends DAOBase<ExerciseSet> implements ISetDAO {

    protected SqliteSetDAO(EntityCreator<ExerciseSet> entityCreator, String table) {
        super(entityCreator, table);
    }

    @Override
    public void saveMany(List<ExerciseSet> sets, int exerciseId) {
        for(ExerciseSet set : sets){
            saveOne(set, exerciseId);
        }
    }

    private void saveOne(ExerciseSet set, int exerciseId){
        ContentValues cv = createCV(set, exerciseId);
        saveToDb(cv);
    }

    private ContentValues createCV(ExerciseSet set, int exerciseId){
        ContentValues cv = new ContentValues();

        cv.put(SetContract.ExerciseSetEntry.EXERCISE_ID, exerciseId);
        cv.put(SetContract.ExerciseSetEntry.SET_REPS, set.getAmount());
        cv.put(SetContract.ExerciseSetEntry.SET_WEIGHT, set.getWeight());
        return cv;
    }

    @Override
    public List<ExerciseSet> getSetsByExerciseId(int id) {
        return selectFromDb("_id=?", createArgs(id));
    }

    @Override
    public void deleteAllSetsFromExercise(int id) {
        removeFromDb("_id=?", createArgs(id));
    }

    @Override
    public void update(ExerciseSet set, int exerciseId) {
       ContentValues cv = createCV(set, exerciseId);
       saveToDb(cv);
    }
}

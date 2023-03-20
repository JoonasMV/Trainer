package com.example.trainer.mainActivity.dao.sqlite;

import static com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.contracts.ExerciseTypeContract;
import com.example.trainer.mainActivity.dao.framework.DAOBase;
import com.example.trainer.mainActivity.dao.framework.IExerciseTypeDAO;
import com.example.trainer.mainActivity.dao.entityCreators.EntityCreator;
import com.example.trainer.schemas.ExerciseType;

import java.util.ArrayList;
import java.util.List;

public class SqliteExerciseTypeDAO extends DAOBase<ExerciseType> implements IExerciseTypeDAO {

    SqliteExerciseTypeDAO(EntityCreator<ExerciseType> entityCreator, String table) {
        super(entityCreator, table);
    }

    @Override
    public int save(ExerciseType exerciseType) {
        ContentValues cv = createCV(exerciseType);
        saveToDb(cv);
        return getIdOfLastInsertedRow();
    }

    private ContentValues createCV(ExerciseType type){
        ContentValues cv = new ContentValues();
        cv.put(ExerciseTypeContract.ExerciseTypeEntry.EXERCISETYPE_NAME, type.getName());
        return cv;
    }

    @Override
    public void deleteById(int id) {
       removeFromDb("_id=?", createArgs(id));
    }

    @Override
    public List<ExerciseType> getAll() {
        SQLiteDatabase db = readableDB();
        String query = "SELECT * FROM " + TABLE_EXERCISETYPE + " ORDER BY exerciseTypeName;";
        Cursor cursor = db.rawQuery(query, null);
        List<ExerciseType> result = new ArrayList<>();
        while(cursor.moveToNext()){
            ExerciseType type = readRow(cursor);
            result.add(type);
        }
        return result;
    }

    @Override
    public ExerciseType getExerciseTypeById(int id) {
        List<ExerciseType> result = selectFromDb("_id=?", createArgs(id));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public ExerciseType getExerciseTypeByName(String name) {
        String clause = String.format("%s=?", ExerciseTypeContract.ExerciseTypeEntry.EXERCISETYPE_NAME);
        List<ExerciseType> result = selectFromDb(clause, createArgs(name));
        return result.isEmpty() ? null : result.get(0);
    }
}

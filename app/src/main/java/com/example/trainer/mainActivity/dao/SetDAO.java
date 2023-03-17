package com.example.trainer.mainActivity.dao;

import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.SET_ID;
import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.SET_REPS;
import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.SET_WEIGHT;
import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.TABLE_SET;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.SetContract;
import com.example.trainer.mainActivity.dao.framework.ISetDAO;
import com.example.trainer.schemas.ExerciseSet;

import java.util.ArrayList;
import java.util.List;

public class SetDAO implements ISetDAO {
    DatabaseHelper dbConnection;

    public SetDAO() {
        dbConnection = DatabaseHelper.getInstance();
    }

    //gets setlist corresponding to the exercsise provided
    //returns the setlist of the exercise
    public List<ExerciseSet> getSetsByExerciseId(int exerciseId) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ArrayList<ExerciseSet> sets = new ArrayList<>();
        try {
            Cursor cursor = db.query(TABLE_SET, null, "exerciseId=?", new String[]{Integer.toString(exerciseId)}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    int amount = cursor.getInt(cursor.getColumnIndexOrThrow("reps"));
                    double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("weight"));
                    ExerciseSet set = new ExerciseSet(weight, amount);
                    set.setId(id);
                    sets.add(set);
                }
            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return sets;

    }

    //adds sets to database
    @Override
    public void saveMany(List<ExerciseSet> sets, int exerciseId){
        try {
            SQLiteDatabase write = dbConnection.getWritableDatabase();
            String setQuery = "INSERT INTO " + TABLE_SET + " (reps, weight, exerciseId) values (?, ?, ?)";
            SQLiteStatement setStatement = write.compileStatement(setQuery);
            for(ExerciseSet e : sets){
                setStatement.bindLong(1, e.getAmount());
                setStatement.bindDouble(2, e.getWeight());
                setStatement.bindLong(3, exerciseId);
                setStatement.executeInsert();
            }

        }catch (SQLException e) {
            Log.w("error", e);
        }
    }
    public void deleteAllSets(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseSet", null, null);
        db.close();
    }
    public void deleteAllSetsFromExercise(int exerciseId){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseSet", "exerciseId=?", new String[] {Integer.toString(exerciseId)});

        db.close();
    }

    @Override
    public void update(ExerciseSet set, int exerciseId) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SET_WEIGHT, set.getWeight());
        values.put(SET_REPS, set.getAmount());
        values.put(SetContract.ExerciseSetEntry.EXERCISE_ID, exerciseId);

        db.update(TABLE_SET, values, String.format("%s=?", SET_ID), new String[]{String.valueOf(set.getId())});
        db.close();
    }
}

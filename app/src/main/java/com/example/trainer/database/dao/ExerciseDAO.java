package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO implements IexerciseDao{
    DatabaseHelper dbConnection;

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    @Override
    public boolean addExercise(String newExercise) {
        newExercise = newExercise.toLowerCase();
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();
        System.out.println(db);

        if (getExercise(newExercise) != null || newExercise.length() == 0) {
            return false;
        }

        try {
            cv.put(ExerciseEntry.EXERCISE_NAME, newExercise);
            System.out.println(cv);
            db.insert(ExerciseEntry.TABLE_EXERCISE, null, cv);

        } catch (Exception e) {
            System.out.println("addExercise()");
            e.printStackTrace();
            return false;
        } finally {
            cv.clear();
        }
        return true;
    }

    @Override
    public Exercise getExercise(String exerciseToQuery) {
        String[] selectedColumns = {
                ExerciseEntry.EXERCISE_NAME,
        };

        SQLiteDatabase db = dbConnection.getWritableDatabase();
        try {
            Cursor cursor = db.query(
                    ExerciseEntry.TABLE_EXERCISE,
                    selectedColumns,
                    ExerciseEntry.EXERCISE_NAME + " = ?",
                    new String[] { exerciseToQuery },
                    null,null,null);

            if (!cursor.moveToFirst()) return null;

            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_NAME));
            return new Exercise(exerciseName);

        } catch (SQLException e) {
            System.out.println("getExerciseById()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getReadableDatabase();

        try {
            Cursor cursor = db.query(ExerciseEntry.TABLE_EXERCISE, null, null,null, null, null, null);

            // get indexes for columns
            int iName = cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_NAME);

            // loop trough cursor and add exercises to list
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                exerciseList.add(new Exercise(cursor.getString(iName)));
            }
        } catch (SQLException e) {
            System.out.println("GetAllExercises()");
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return exerciseList;
    }

    @Override
    public int addManyExercises(List<Exercise> exercises) {
        return 0;
    }

    public void deleteAllExercises(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", null, null);
        Log.d("m", "deleted");
        db.close();
    }
}

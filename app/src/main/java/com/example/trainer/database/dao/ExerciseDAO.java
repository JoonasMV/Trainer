package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO implements IexerciseDao{
    DatabaseHelper dbConnection;

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    @Override
    public void addExercise(String newExercise) {
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(ExerciseEntry.EXERCISE_NAME, newExercise);
            System.out.println(cv);
            db.insert(ExerciseEntry.TABLE_EXERCISE, null, cv);

        } catch (Exception e) {
            System.out.println("addExercise()");
            e.printStackTrace();
        } finally {
            db.close();
            cv.clear();
        }
    }

    @Override
    public Exercise getExerciseById(int id) {
        String[] selectedColumns = {
                ExerciseEntry.EXERCISE_ID,
                ExerciseEntry.EXERCISE_NAME,
                //ExerciseEntry.WORKOUT_ID
        };

        SQLiteDatabase db = dbConnection.getWritableDatabase();
        try {
            Cursor cursor = db.query(
                    ExerciseEntry.TABLE_EXERCISE,
                    selectedColumns,
                    ExerciseEntry.EXERCISE_ID + " = ?",
                    new String[] {String.valueOf(id)},
                    null,null,null);

            if (!cursor.moveToFirst()) return null;

            int exerciseId = cursor.getInt(cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_ID));
            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_NAME));
            return new Exercise(exerciseName, exerciseId);

        } catch (SQLException e) {
            System.out.println("getExerciseById()");
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    @Override
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getReadableDatabase();

        try {
            Cursor cursor = db.query(ExerciseEntry.TABLE_EXERCISE, null, null,null, null, null, null);

            // get indexes for columns
            int iId = cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_ID);
            int iName = cursor.getColumnIndexOrThrow(ExerciseEntry.EXERCISE_NAME);

            // loop trough cursor and add exercises to list
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                exerciseList.add(new Exercise(cursor.getString(iName), cursor.getInt(iId)));
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



    public ArrayList<Exercise> getExercisesByName(String name) {
        return null;
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

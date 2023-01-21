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

    public ArrayList<Exercise> addTestExercises () {

        deleteAllExercises();
        ArrayList<ExerciseSet> sets = new ArrayList<>();
        sets.add(new ExerciseSet("penkki", 1, 4));
        sets.add(new ExerciseSet("penkki", 4, 45));
        sets.add(new ExerciseSet("penkki", 45, 56));

//        ArrayList<Exercise> testi = new ArrayList<>();
//        testi.add(new Exercise("bench",1));
//        testi.add(new Exercise("squat",2));
//        testi.add(new Exercise("deadlift",3));

        String[] exTestList = {"bench", "squat", "deadlift"};

        for (String name : exTestList) {
            addExercise(name);
        }
        return null;
    }

    @Override
    public void addExercise(String newExercise) {
        //System.out.println("ADD EXERCISE");
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

    //TODO: not fully implemented
    @Override
    public Exercise getExerciseById(int id) {
        String[] selectedColumns = {
                ExerciseEntry.EXERCISE_ID,
                ExerciseEntry.EXERCISE_NAME,
                //ExerciseEntry.WORKOUT_ID
        };

        String query =
                "SELECT * FROM " + ExerciseEntry.TABLE_EXERCISE +
                        " WHERE " + ExerciseEntry.EXERCISE_ID + " =?";

        SQLiteDatabase db = dbConnection.getWritableDatabase();
        try {
            Cursor cursor = db.query(
                    ExerciseEntry.TABLE_EXERCISE,
                    selectedColumns,
                    query,
                    new String[] {String.valueOf(id)},
                    null,null,null);
        } catch (Exception e) {

        } finally {
            db.close();
        }

        return null;
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

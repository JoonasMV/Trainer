package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry;
import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.SetContract.ExerciseSeEntry.TABLE_SET;

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
import java.util.Set;

public class ExerciseDAO implements IexerciseDao{
    DatabaseHelper dbConnection;

    SetDAO setDAO;

    public ExerciseDAO(Context context) {

        dbConnection = DatabaseHelper.getInstance(context);
        setDAO = new SetDAO(context);
    }



    //adds an exercise to database and also adds sets to database that the exercise has
    //returns the id of the exercise added
    public int addExercise(Exercise exercise) {
        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        List<ExerciseSet> sets = exercise.getSetList();


        try {
            String query = "INSERT INTO " + TABLE_EXERCISE + " (exerciseName) values (?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, exercise.getExerciseName());
            statement.executeInsert();


            SQLiteDatabase read = dbConnection.getReadableDatabase();
            Cursor cursor = read.query(TABLE_EXERCISE, null, null, null, null, null, null);

            if(cursor != null) {
                cursor.moveToLast();
                id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }

            if(id == -1){
                Log.d("error", "no id found");
            }


            if(!sets.isEmpty()){
                setDAO.addSetsToDb(sets, id);

            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;
    }





    //gets an exercise from database based on id
    //returns exercise
    public Exercise getExerciseById(int id){
        Exercise exercise = null;
        String[] args = new String[] {Integer.toString(id)};
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, null, "_id=?", args, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                exercise = readExercise(cursor);
                List<ExerciseSet> sets = setDAO.getSetsByExerciseId(exercise.getExerciseId());
                exercise.setSetList(sets);
            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return exercise;
    }


    //gets all exercises from database
    //returns list of all exercises
    @Override
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_EXERCISE + ";";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExercise(cursor);
                    List<ExerciseSet> sets = setDAO.getSetsByExerciseId(exercise.getExerciseId());
                    exercise.setSetList(sets);
                    exercises.add(exercise);
                }
            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return exercises;
    }


    //reads exercise information from database and creates a new exercise object
    //returns the newly created exercise
    private Exercise readExercise(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseName"));
        Exercise exercise = new Exercise(name, id);
        return exercise;
    }


    //get an exercise by name
    //returns an arraylist of exercises matching the given name
    public ArrayList<Exercise> getExercisesByName(String name) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] args = new String[] {name};
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, null, "exerciseName LIKE ?", args, null, null, null);
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExercise(cursor);
                    List<ExerciseSet> sets = setDAO.getSetsByExerciseId(exercise.getExerciseId());
                    exercise.setSetList(sets);
                    exercises.add(exercise);
                }
            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return exercises;
    }

    @Override
    public int addManyExercises(List<Exercise> exercises) {
        return 0;
    }


    //self explanatory
    public void deleteAllExercises(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", null, null);
        Log.d("m", "deleted");
        db.close();
    }
}

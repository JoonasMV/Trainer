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

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }



    public int addExercise(Exercise exercise) {
        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        List<ExerciseSet> sets = exercise.getSetList();


        try {
            String query = "INSERT INTO " + TABLE_EXERCISE + " (exerciseName) values (?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, exercise.getExerciseName());
            id = statement.executeInsert();


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
                SQLiteDatabase write = dbConnection.getWritableDatabase();
                String setQuery = "INSERT INTO " + TABLE_SET + " (exerciseSetName, amount, weight, exerciseId) values (?, ?, ?, ?)";
                SQLiteStatement setStatement = write.compileStatement(setQuery);
                for(ExerciseSet e : sets){
                    setStatement.bindString(1, e.getName());
                    setStatement.bindLong(2, e.getAmount());
                    setStatement.bindDouble(3, e.getWeight());
                    setStatement.bindLong(4, id);
                    setStatement.executeInsert();
                }

            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;
    }



    private List<ExerciseSet> getSets(int exId, SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SET, null, "exerciseId LIKE ?", new String[]{Integer.toString(exId)}, null, null, null);
        ArrayList<ExerciseSet> sets = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                int id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseSetName"));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("weight"));
                ExerciseSet set = new ExerciseSet(name, weight, amount);
                set.setId(id);
                sets.add(set);
            }
        }
        return sets;
    }

    public Exercise getExerciseById(int id){
        Exercise exercise = null;
        String[] args = new String[] {Integer.toString(id)};
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, null, "_id=?", args, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                exercise = readExercise(cursor);
            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return exercise;
    }

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
                    List<ExerciseSet> sets = getSets(exercise.getExerciseId(), db);
                    exercise.setSetList(sets);
                    exercises.add(exercise);
                }
            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return exercises;
    }

    private Exercise readExercise(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseName"));
        Exercise exercise = new Exercise(name, id);
        return exercise;
    }

    public ArrayList<Exercise> getExercisesByName(String name) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String[] args = new String[] {name};
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, null, "exerciseName LIKE ?", args, null, null, null);
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExercise(cursor);
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

    public void deleteAllExercises(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", null, null);
        Log.d("m", "deleted");
        db.close();
    }
}

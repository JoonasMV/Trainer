package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.contracts.ExerciseContract;
import com.example.trainer.database.schemas.Exercise;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ExerciseDAO implements IexerciseDao{
    DatabaseHelper dbConnection;

    public ExerciseDAO(Context context) {
        dbConnection = DatabaseHelper.getInstance(context);
    }

    public ArrayList<Exercise> addTestExercises () {

        deleteAllExercises();
        ArrayList<Exercise> testi = new ArrayList<>();
        testi.add(new Exercise(1, 2, "testi"));
        testi.add(new Exercise(1, 2, "testi2"));
        testi.add(new Exercise(1, 2, "testi3"));


        for (Exercise e : testi) {
            int num = addExercise(e);
        }
        return testi;
    }

    @Override
    public ArrayList<Exercise> getAllExercises() {


        ArrayList<Exercise> exercises = new ArrayList<>();


        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_EXERCISE + ";";
            Cursor cursor = db.rawQuery(query, null);

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

    private Exercise readExercise(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        int set = cursor.getInt(cursor.getColumnIndexOrThrow("setNumber"));
        int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseName"));

        Exercise exercise = new Exercise(set, weight, name, id);
        return exercise;
    }

    @Override
    public Exercise getExerciseById(int id) {
        return null;
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
    public int addExercise(Exercise exercise) {
        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();


        try {

            String query = "INSERT INTO " + TABLE_EXERCISE + " (setNumber, weight, exerciseName) values (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindLong(1, exercise.getSetNumber());
            statement.bindDouble(2, exercise.getWeight());
            statement.bindString(3, exercise.getName());;
            id = statement.executeInsert();

        }catch (SQLException e) {
            Log.w("error", e);
        }
        finally {

            db.close();
        }

        return (int) id;
    }

    @Override
    public int addManyExercises(ArrayList<Exercise> exercises) {
        return 0;
    }

    public void deleteAllExercises(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", null, null);
        Log.d("m", "deleted");
        db.close();
    }


}

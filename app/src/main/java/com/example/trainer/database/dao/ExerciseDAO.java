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

        Log.d("d", "lista tehty");

        for (Exercise e : testi) {
            int num = addExercise(e);
            Log.d("lisatty", e.getName() + num);
        }
        return testi;
    }

    @Override
    public ArrayList<Exercise> getAllExercises() {


        ArrayList<Exercise> exercises = new ArrayList<>();


        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Log.d("m", "luetaan");
            String query = "SELECT * FROM " + TABLE_EXERCISE + ";";
            Log.d("d", query);
            Cursor cursor = db.rawQuery(query, null);

            if(cursor != null) {
                Log.d("m", "cursori");
                Log.d("m", Integer.toString(cursor.getColumnCount()));
                Log.d("m", Integer.toString(cursor.getCount()));
                while (cursor.moveToNext()) {
                    Log.d("m", "luetaan cursor");
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
        Log.d("m", "ollaan readEx");
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        int set = cursor.getInt(cursor.getColumnIndexOrThrow("setNumber"));
        int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseName"));

        Exercise exercise = new Exercise(set, weight, name, id);
        Log.d("id", Integer.toString(id));
        Log.d("set", Integer.toString(set));
        Log.d("weight", Integer.toString(weight));
        Log.d("name", name);
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
            Log.d("m", "luetaan");

            Cursor cursor = db.query(TABLE_EXERCISE, null, "exerciseName LIKE ?", args, null, null, null);

            if(cursor != null) {
                Log.d("m", "cursori");
                Log.d("m", Integer.toString(cursor.getColumnCount()));
                Log.d("m", Integer.toString(cursor.getCount()));
                while (cursor.moveToNext()) {
                    Log.d("m", "luetaan cursor");
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
            Log.d("m", query);
            statement.bindLong(1, exercise.getSetNumber());
            statement.bindDouble(2, exercise.getWeight());
            Log.d("m", "name " + exercise.getName());
            statement.bindString(3, exercise.getName());
            Log.d("m", statement.toString());
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

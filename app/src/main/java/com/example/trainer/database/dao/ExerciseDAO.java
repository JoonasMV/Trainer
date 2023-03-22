package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.EXERCISE_ID;
import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.EXERCISE_TYPEID;
import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.ISetDAO;
import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO implements IExerciseDAO {
    DatabaseHelper dbConnection;

    ISetDAO setDAO;

    private String columns = "exerciseType._id AS typeId, exerciseType.exerciseTypeName AS name, exercise.workoutId, exercise._id AS id";
    private String sqlQuery = "SELECT " + columns + " FROM " + TABLE_EXERCISETYPE + " INNER JOIN " + TABLE_EXERCISE + " ON exercise.exerciseTypeId = exerciseType._id;";

    private String whereQ = "SELECT " + columns + " FROM " + TABLE_EXERCISETYPE + " INNER JOIN " + TABLE_EXERCISE + " ON exercise.exerciseTypeId = exerciseType._id WHERE ";


    public ExerciseDAO() {
        dbConnection = DatabaseHelper.getInstance();
        setDAO = new SetDAO();
    }

    //adds an exercise to database and also adds sets to database that the exercise has
    //returns the id of the exercise added
    @Override
    public int save(Exercise exercise) {
        int id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        List<ExerciseSet> sets = exercise.getSetList();


        try {
            String query = "INSERT INTO " + TABLE_EXERCISE + " (workoutId, exerciseTypeId) values (?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindLong(1, exercise.getWorkoutId());
            statement.bindLong(2, exercise.getTypeId());
            statement.executeInsert();

            SQLiteDatabase read = dbConnection.getReadableDatabase();
            Cursor cursor = read.query(TABLE_EXERCISE, null, null, null, null, null, null);

            if (cursor != null) {
                cursor.moveToLast();
                id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }

            if (id == -1) {
                Log.d("error", "no id found");
            }


            if (!sets.isEmpty()) {
                setDAO.saveMany(sets, id);

            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;
    }


    //gets an exercise from database based on id
    //returns exercise
    @Override
    public Exercise getById(int id) {
        List<Exercise> e = selectFromDb("id=?;", new String[] {Integer.toString(id)});
        if(e == null) return null;
        if(e.isEmpty()) return null;
        return e.get(0);
    }

    @Override
    public List<Exercise> getByWorkoutId(int id) {
        return selectFromDb("exercise.workoutId=?",new String[] { Integer.toString(id) });
    }



    //reads exercise information from database and creates a new exercise object
    //returns the newly created exercise

    private Exercise readExerciseRow(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("typeId"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int workoutId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("workoutId"));
        List<ExerciseSet> sets = setDAO.getSetsByExerciseId(id);
        return new Exercise(name, id, workoutId, typeId, sets);
    }

    @Override
    public void saveMany(List<Exercise> exercises) {
        for (Exercise e : exercises){
            save(e);
        }
    }


    //self explanatory
    public void deleteAll(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        db.delete("exercise", null, null);
    }



    @Override
    public void delete(Exercise exercise){
        int id = exercise.getExerciseId();
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        db.delete("exercise", "_id=?", new String[] {Integer.toString(id)});
        db.close();
        setDAO.deleteAllSetsFromExercise(id);
    }

    private List<Exercise> selectFromDb(String whereClause, String[] args) {
        List<Exercise> exercises = new ArrayList<>();
        String whereQuery = whereQ + whereClause;
        Log.d("DATABASE", whereQuery);
        Log.d("ID search ", args[0]);
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.rawQuery(whereQuery, args);
            Log.d("CURSOR", String.valueOf(cursor == null));
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExerciseRow(cursor);
                    Log.d("EXERCISE", exercise.getExerciseName());
                    exercises.add(exercise);
                }
            }
        }catch (SQLException e) {
            Log.w("error", e);
            return null;
        }

        return exercises;
    }


    /*  -> exerciseType methods  */
    public int addExerciseType(ExerciseType exerciseType) {
        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        try {
            String query = "INSERT INTO " + TABLE_EXERCISETYPE + " (exerciseTypeName) values (?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, exerciseType.getName().toLowerCase());
            statement.executeInsert();
            SQLiteDatabase read = dbConnection.getReadableDatabase();
            Cursor cursor = read.query(TABLE_EXERCISETYPE, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToLast();
                id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
            if (id == -1) {
                Log.d("error", "no id found");
            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;
    }

    public void deleteExerciseTypeById(int id){
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        db.delete("exerciseType", "_id=?", new String[] {Integer.toString(id)});
        db.delete("exercise", "exerciseTypeId=?", new String[] {Integer.toString(id)});

        db.close();
    }

    public void deleteAllExerciseTypes(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        deleteAll();
        db.delete("exerciseType", null, null);
    }

    public List<ExerciseType> getAllExerciseTypes() {
        List<ExerciseType> exerciseTypes = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_EXERCISETYPE + " ORDER BY exerciseTypeName;";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseTypeName"));
                    ExerciseType exerciseType = new ExerciseType(name);
                    exerciseType.setId(id);
                    exerciseTypes.add(exerciseType);
                }
            }
        } catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return exerciseTypes;
    }

    public ExerciseType getExerciseTypeById(int id) {
        ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISETYPE, null, "_id=?", new String[] {Integer.toString(id)}, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseTypeName"));
                ExerciseType exerciseType = new ExerciseType(name);
                exerciseType.setId(typeId);
                return exerciseType;
            }
        }catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return null;
    }

    public ExerciseType getExerciseTypeByName(String name) {
        ExerciseType exerciseType = null;
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISETYPE, null, "exerciseTypeName=?", new String[] {name}, null, null, null);
            if(cursor != null && cursor.moveToFirst() ) {
                int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                String exerciseTypeName = cursor.getString(cursor.getColumnIndexOrThrow("exerciseTypeName"));
                exerciseType = new ExerciseType(exerciseTypeName);
                exerciseType.setId(typeId);
                return exerciseType;
            }

        }catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return null;
    }

    @Override
    public void update(Exercise exercise){

        SQLiteDatabase db = dbConnection.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EXERCISE_TYPEID, exercise.getTypeId());

        db.update(TABLE_EXERCISE, values, String.format("%s=?", EXERCISE_ID), new String[]{String.valueOf(exercise.getExerciseId())});
        db.close();

        List<ExerciseSet> sets = exercise.getSetList();

        for(ExerciseSet set : sets){
            setDAO.update(set, exercise.getExerciseId());
        }

    }

}

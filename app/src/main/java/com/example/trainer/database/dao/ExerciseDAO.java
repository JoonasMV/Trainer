package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;
import static com.example.trainer.database.contracts.SetContract.ExerciseSetEntry.TABLE_SET;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.database.schemas.Workout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseDAO {
    DatabaseHelper dbConnection;

    SetDAO setDAO;

    private String columns = "exerciseType._id AS typeId, exerciseType.exerciseTypeName AS name, exercise.workoutId, exercise._id AS id";
    private String sqlQuery = "SELECT " + columns + " FROM " + TABLE_EXERCISETYPE + " INNER JOIN " + TABLE_EXERCISE + " ON exercise.exerciseTypeId = exerciseType._id;";

    private String whereQ = "SELECT " + columns + " FROM " + TABLE_EXERCISETYPE + " INNER JOIN " + TABLE_EXERCISE + " ON exercise.exerciseTypeId = exerciseType._id WHERE ";


    public ExerciseDAO() {
        dbConnection = DatabaseHelper.getInstance();
        setDAO = new SetDAO();


    }

    private void test(){
        Log.d("mfsf", "dfs");
        ExerciseType t = new ExerciseType("testi");
        int id = addExerciseType(t);

        Log.d("DATABASE", Integer.toString(id));

        Workout w = new Workout("testiW", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));

        WorkoutDAO dao = new WorkoutDAO();

        int wID = dao.add(w);

        Log.d("DATABASE", Integer.toString(wID));

        Exercise e = new Exercise("nimi", wID, id);

        int eId = addExercise(e);

        ArrayList<ExerciseSet> sets = new ArrayList<>();

        sets.add(new ExerciseSet(20, 100));


        setDAO.addSetsToDb(sets, eId);

        Log.d("DATABASE", Integer.toString(eId));

    }


    //adds an exercise to database and also adds sets to database that the exercise has
    //returns the id of the exercise added
    public int addExercise(Exercise exercise) {
        long id = -1;
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
                id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }

            if (id == -1) {
                Log.d("error", "no id found");
            }


            if (!sets.isEmpty()) {
                setDAO.addSetsToDb(sets, id);

            }
        } catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;
    }


    //gets an exercise from database based on id
    //returns exercise
    public Exercise getExerciseById(int id) {
        List<Exercise> e = selectFromDb("id=?;", new String[] {Integer.toString(id)});
        Log.d("DATABASE", e.get(0).getExerciseName());
        return selectExFromDb(id);
    }

    public List<Exercise> getExerciseByWorkoutId(int id) {
        return selectFromDb("workoutId=?;", new String[] {Integer.toString(id)});
    }


    //gets all exercises from database
    //returns list of all exercises
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            test();
            Log.d("DATABASE", "database");
            SQLiteDatabase db = dbConnection.getReadableDatabase();

            Log.d("DATABASE", sqlQuery);

            Cursor cursor = db.rawQuery(sqlQuery, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExerciseRow(cursor);
                    List<ExerciseSet> sets = setDAO.getSetsByExerciseId(exercise.getExerciseId());
                    exercise.setSetList(sets);
                    exercises.add(exercise);
                }
            }
        } catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return exercises;
    }

    public void addSetsToExercise(List<ExerciseSet> sets, int exerciseId) {
        try {
            SQLiteDatabase write = dbConnection.getWritableDatabase();
            String setQuery = "INSERT INTO " + TABLE_SET + " (reps, weight, exerciseId) values (?, ?, ?)";
            SQLiteStatement setStatement = write.compileStatement(setQuery);
            for (ExerciseSet e : sets) {
                setStatement.bindLong(1, e.getAmount());
                setStatement.bindDouble(2, e.getWeight());
                setStatement.bindLong(3, exerciseId);
                setStatement.executeInsert();
            }

        } catch (SQLException e) {
            Log.w("error", e);
        }
    }




    public void deleteAllSets(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseSet", null, null);
        db.close();
    }


    private Exercise selectExFromDb(int id){
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, null, "_id=?", new String[] {Integer.toString(id)}, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("exerciseTypeId"));
                int workoutId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("workoutId"));
                ExerciseType type = getExerciseTypeById(typeId);
                List<ExerciseSet> sets = setDAO.getSetsByExerciseId(id);

                System.out.println(type);
                System.out.println(type.getName());

                return new Exercise(type.getName(), id, workoutId, typeId, sets);
            }
        }catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return null;
    }



    //reads exercise information from database and creates a new exercise object
    //returns the newly created exercise

    private Exercise readExerciseRow(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("typeId"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int workoutId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("workoutId"));
        ExerciseType type = getExerciseTypeById(typeId);
        List<ExerciseSet> sets = setDAO.getSetsByExerciseId(id);

        if(sets == null){
            sets = new ArrayList<>();
        }


        return new Exercise(type.getName(), id, workoutId, typeId, sets);
    }


    //get an exercise by name
    //returns an arraylist of exercises matching the given name
    public List<Exercise> getExercisesByName(String name) {
        return selectFromDb("name LIKE ?;",new String[] {name});
    }

    public void addManyExercises(List<Exercise> exercises) {
        for (Exercise e : exercises){
            addExercise(e);
        }
    }


    //self explanatory
    public void deleteAllExercises(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", null, null);
        db.close();
    }

    public void deleteSetById(int id){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseSet", "_id=?", new String[] {Integer.toString(id)});

        db.close();
    }

    private void deleteAllSetsFromExercise(int exerciseId){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseSet", "exerciseId=?", new String[] {Integer.toString(exerciseId)});

        db.close();
    }


    public void deleteExerciseById(int id){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exercise", "_id=?", new String[] {Integer.toString(id)});
        deleteAllSetsFromExercise(id);
        db.close();
    }

    private List<Exercise> selectFromDb(String whereClause, String[] args) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String whereQuery = whereQ + whereClause;
        Log.d("DATABASE", whereQuery);
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.rawQuery(whereQuery, args);
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Exercise exercise = readExerciseRow(cursor);
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
            statement.bindString(1, exerciseType.getName());
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
        deleteAllSetsFromExercise(id);
        db.close();
    }

    public void deleteAllExerciseTypes(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("exerciseType", null, null);
        db.close();
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
                System.out.println("NAME" + name);
                System.out.println("ID" + typeId);
                return exerciseType;
            }
        }catch (SQLException e) {
            Log.w("error", e);
            return null;
        }
        return null;
    }





}

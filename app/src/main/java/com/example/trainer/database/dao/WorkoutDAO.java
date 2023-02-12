package com.example.trainer.database.dao;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.TABLE_WORKOUT;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_ID;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutDAO {
    DatabaseHelper dbConnection;

    ExerciseDAO exerciseDAO;

    public WorkoutDAO() {
        dbConnection = DatabaseHelper.getInstance();
        exerciseDAO = new ExerciseDAO();
    }


    public List<Workout> getPresets() {
        List<Workout> results = selectFromDb(null, "isPreset=?", new String[] {Integer.toString(1)}, null, null, null);
        return results;
    }


    public List<Workout> getAll() {
        List<Workout> results = selectFromDb(null, null, null, null, null, null);
        return results;
    }

    public List<Workout> getAllByUserId(int userId) {
        List<Workout> results = selectFromDb(null, "userId=?", new String[] {Integer.toString(userId)}, null, null, null);
        return results;
    }

    private Workout readWorkoutRow(Cursor cursor) throws ParseException {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("workoutName"));
        String started = cursor.getString(cursor.getColumnIndexOrThrow("workoutStarted"));
        String ended = cursor.getString(cursor.getColumnIndexOrThrow("workoutEnded"));
        int user = (int) cursor.getLong(cursor.getColumnIndexOrThrow("userId"));
        int preset = (int)cursor.getLong(cursor.getColumnIndexOrThrow("isPreset"));

        Date start = parseDate(started);
        Date end = parseDate(ended);
        Workout workout = new Workout(name, start, end);
        if(preset == 1){
            workout.setPreset(true);
        }
        workout.setUserId(user);
        workout.setId(id);

        return workout;
    }

    public Workout getById(int id) {
        String[] args = new String[] {Integer.toString(id)};

        List<Workout> results = selectFromDb(null, "_id=?", args, null, null, null);

        if(results.isEmpty()) return null;

        return results.get(0);
    }




    public int add(Workout workout) {

        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();


        List<Exercise> exercises = workout.getExList();


        try {
            String query = "INSERT INTO " + TABLE_WORKOUT + " (workoutName, workoutStarted, workoutEnded, userId, isPreset) values (?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1, workout.getName());
            statement.bindString(2, workout.getWorkoutStarted().toString());
            statement.bindString(3, workout.getWorkoutEnded().toString());
            statement.bindLong(4, workout.getUserId());
            if(workout.isPreset()){
                statement.bindLong(5, 1);
            }else {
                statement.bindLong(5,0);
            }

            statement.executeInsert();


            SQLiteDatabase read = dbConnection.getReadableDatabase();
            Cursor cursor = read.query(TABLE_WORKOUT, null, null, null, null, null, null);

            if(cursor != null) {
                cursor.moveToLast();
                id = (int) cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }

            if(id == -1){
                Log.d("error", "no id found");
            }

            if(!exercises.isEmpty()){
                exerciseDAO.addManyExercises(exercises);

            }
        }catch (SQLException e) {
            Log.w("error", e);
        }
        return (int) id;


    }

    private Date parseDate(String target) throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        return df.parse(target);
    }

    public void addMany(List<Workout> workouts) {
        for(Workout w : workouts) {
            add(w);
        }
    }

    public List<Workout> getByName(String name) {
        String[] args = new String[] {name};
        return selectFromDb(null, "workoutName LIKE ?", args, null, null, null);
    }

    private List<Workout> selectFromDb(String[] columns, String clause, String[] args, String groupBy, String having, String orderBy) {
        ArrayList<Workout> workouts = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_EXERCISE, columns, clause, args, groupBy, having, orderBy);
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Workout workout = readWorkoutRow(cursor);
                    List<Exercise> exercises = exerciseDAO.getExerciseByWorkoutId(workout.getId());
                    workout.setExList(exercises);
                    workouts.add(workout);
                }
            }
        }catch (SQLException | ParseException e) {
            Log.w("error", e);
            return null;
        }

        return workouts;
    }

    public void deleteAllWorkouts(){
        SQLiteDatabase db = dbConnection.getWritableDatabase();

        db.delete("workout", null, null);
        db.close();
    }
}

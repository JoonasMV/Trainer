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

public class WorkoutDAO implements IworkoutDao{
    DatabaseHelper dbConnection;

    ExerciseDAO exerciseDAO;

    public WorkoutDAO(Context context) {

        dbConnection = DatabaseHelper.getInstance(context);
        exerciseDAO = new ExerciseDAO(context);
        add(new Workout("testi", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        add(new Workout("testi2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        add(new Workout("testi3", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        add(new Workout("testi4", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        Log.d("d", "added");
        ArrayList<Workout> workouts= getAll();
        for (Workout w : workouts){
            Log.d("D", w.getName());
            Log.d("Date", w.getWorkoutStarted().toString());
        }
    }


    @Override
    public ArrayList<Workout> getAll() {
        ArrayList<Workout> workouts = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_WORKOUT + ";";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Workout workout = readWorkoutRow(cursor);
                    List<Exercise> exercises = exerciseDAO.getExerciseByWorkoutId(workout.getId());
                    workout.setExList(exercises);
                    workouts.add(workout);
                }
            }
        } catch (SQLException | ParseException e) {
            Log.w("error", e);
        }
        return workouts;
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

    @Override
    public Workout getById(int id) {
        return null;
    }

    @Override
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

    @Override
    public int addMany(List<Workout> workouts) {


        return 0;
    }
}

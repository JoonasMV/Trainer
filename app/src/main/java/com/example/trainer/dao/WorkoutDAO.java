package com.example.trainer.dao;

import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.PRESET;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.TABLE_WORKOUT;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_ENDED;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_ID;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_NAME;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_STARTED;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.trainer.dao.framework.IExerciseDAO;
import com.example.trainer.dao.framework.IWorkoutDAO;
import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutDAO implements IWorkoutDAO {
    DatabaseHelper dbConnection;
    IExerciseDAO exerciseDAO;



    public WorkoutDAO() {
        dbConnection = DatabaseHelper.getInstance();
        exerciseDAO = new ExerciseDAO();
    }


    public List<Workout> getPresets() {
        return selectFromDb(null, "isPreset=?", new String[] {Integer.toString(1)}, null, null, null);
    }

    public List<Workout> getNonPresets() {
        List<Workout> results = selectFromDb(null, "isPreset=?", new String[] {Integer.toString(0)}, null, null, null);
        return results;
    }
    public List<Workout> getUserPresets(int userId) {
        return selectFromDb(null, "isPreset=? AND userId=?", new String[] {Integer.toString(1), Integer.toString(userId)}, null, null, null);
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
        Workout workout;
        String name = cursor.getString(cursor.getColumnIndexOrThrow("workoutName"));
        int user = (int) cursor.getLong(cursor.getColumnIndexOrThrow("userId"));
        int preset = (int)cursor.getLong(cursor.getColumnIndexOrThrow("isPreset"));
        if(preset == 1){
            workout = new Workout(name);
            workout.setPreset(true);
        }else {
            String started = cursor.getString(cursor.getColumnIndexOrThrow("workoutStarted"));
            String ended = cursor.getString(cursor.getColumnIndexOrThrow("workoutEnded"));
            Date start = parseDate(started);
            Date end = parseDate(ended);
            workout = new Workout(name, start, end);

        }
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
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

    public void makePreset(Workout workout){
        workout.setPreset(true);
        save(workout);
    }

    public void update(Workout workout){
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(WORKOUT_NAME, workout.getName());
        values.put(WORKOUT_ENDED, workout.getWorkoutEnded().toString());
        values.put(WORKOUT_STARTED, workout.getWorkoutStarted().toString());
        values.put(PRESET, workout.isPreset());

        db.update(TABLE_WORKOUT, values, String.format("%s=?", WORKOUT_ID), new String[]{String.valueOf(workout.getId())});
        db.close();

        List<Exercise> exercises = workout.getExList();

        for(Exercise e : exercises){
            exerciseDAO.update(e);
        }
    }

    public void delete(Workout workout){
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        db.delete(TABLE_WORKOUT, String.format("%s=?", WORKOUT_ID), new String[] {String.valueOf(workout.getId())});
        db.close();
        for(Exercise e : workout.getExList()) {
            exerciseDAO.delete(e);
        }
    }
    public int save(Workout workout) {

        long id = -1;
        SQLiteDatabase db = dbConnection.getWritableDatabase();
        List<Exercise> exercises = workout.getExList();

        try {

            String query = "INSERT INTO " + TABLE_WORKOUT + " (workoutName, workoutStarted, workoutEnded, userId, isPreset) values (?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(query);
            if(workout.isPreset()){
                statement.bindString(1, workout.getName());
                statement.bindLong(4, workout.getUserId());
                statement.bindLong(5, 1);
            }else {
                statement.bindString(1, workout.getName());
                statement.bindString(2, workout.getWorkoutStarted().toString());
                statement.bindString(3, workout.getWorkoutEnded().toString());
                statement.bindLong(4, workout.getUserId());
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
                for(Exercise e : exercises){
                    e.setWorkoutId((int) id);
                }
                exerciseDAO.saveMany(exercises);

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


    private List<Workout> selectFromDb(String[] columns, String clause, String[] args, String groupBy, String having, String orderBy) {
        ArrayList<Workout> workouts = new ArrayList<>();
        try {
            SQLiteDatabase db = dbConnection.getReadableDatabase();
            Cursor cursor = db.query(TABLE_WORKOUT, columns, clause, args, groupBy, having, orderBy);
            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Workout workout = readWorkoutRow(cursor);
                    List<Exercise> exercises = exerciseDAO.getByWorkoutId(workout.getId());
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
        db.delete("exercise", null, null);
        db.delete("exerciseSet", null, null);
        db.close();
    }

    public void initPresets() {
//        Exercise squatExercise = new Exercise(exerciseDAO.getExerciseTypeByName("squat").getId());
//        Exercise benchExercise = new Exercise(exerciseDAO.getExerciseTypeByName("bench press").getId());
//        Exercise deadliftExercise = new Exercise(exerciseDAO.getExerciseTypeByName("deadlift").getId());
//        Exercise barbellRowExercise = new Exercise(exerciseDAO.getExerciseTypeByName("barbell row").getId());
//        Exercise overheadPressExercise = new Exercise(exerciseDAO.getExerciseTypeByName("overhead press").getId());
//
//        for (int i = 0; i < 5; i++) {
//            squatExercise.addSet(new ExerciseSet());
//            deadliftExercise.addSet(new ExerciseSet());
//            benchExercise.addSet(new ExerciseSet());
//            barbellRowExercise.addSet(new ExerciseSet());
//            overheadPressExercise.addSet(new ExerciseSet());
//        }
//
//        // beginner 5x5 program
//        Workout monday5x5 = new Workout("Monday 5x5", true);
//        monday5x5.getExList().add(squatExercise);
//        monday5x5.getExList().add(benchExercise);
//        monday5x5.getExList().add(barbellRowExercise);
//
//        Workout wednesday5x5 = new Workout("Wednesday 5x5", true);
//        wednesday5x5.getExList().add(deadliftExercise);
//        wednesday5x5.getExList().add(benchExercise);
//        wednesday5x5.getExList().add(overheadPressExercise);
//
//        Workout friday5x5 = new Workout("Friday 5x5", true);
//        friday5x5.getExList().add(squatExercise);
//        friday5x5.getExList().add(overheadPressExercise);
//        friday5x5.getExList().add(barbellRowExercise);
//
//        save(monday5x5);
//        save(wednesday5x5);
//        save(friday5x5);
//        // ---------------------------------------------------------------
    }
}

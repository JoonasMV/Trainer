package com.example.trainer.database.dao.sqlite;

import static com.example.trainer.database.contracts.ExerciseContract.ExerciseEntry.TABLE_EXERCISE;
import static com.example.trainer.database.contracts.ExerciseTypeContract.ExerciseTypeEntry.TABLE_EXERCISETYPE;
import static com.example.trainer.database.contracts.UserContract.UserEntry.TABLE_USER;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.PRESET;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.TABLE_WORKOUT;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_ENDED;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_NAME;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_STARTED;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainer.database.contracts.WorkoutContract;
import com.example.trainer.database.dao.entityCreators.EntityCreator;
import com.example.trainer.database.dao.framework.DAOBase;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;

import java.util.ArrayList;
import java.util.List;

public class SqliteWorkoutDAO extends DAOBase<Workout> implements IWorkoutDAO {

    IExerciseDAO exerciseDAO;

    protected SqliteWorkoutDAO(EntityCreator<Workout> entityCreator, String table) {
        super(entityCreator, table);
        this.exerciseDAO = new SqliteExerciseDAO();
    }

    @Override
    public List<Workout> getPresets() {
        String clause = String.format("%s=?", PRESET);
        return selectFromDb(clause, createArgs(1));
    }

    @Override
    public List<Workout> getNonPresets() {
        String clause = String.format("%s=?", PRESET);
        return selectFromDb(clause, createArgs(0));
    }

    @Override
    public List<Workout> getAll() {
        SQLiteDatabase db = readableDB();
        //TODO: sort by date
        String query = "SELECT * FROM " + TABLE_WORKOUT;
        System.out.println("QUERY ---- " +query);
        Cursor cursor = db.rawQuery(query, null);
        List<Workout> result = new ArrayList<>();
        while(cursor.moveToNext()){
            Workout type = readRow(cursor);
            result.add(type);
        }
        return result;

//        return selectFromDb(null, null);
    }

    @Override
    public Workout getById(String id) {
        List<Workout> result = selectFromDb(WorkoutContract._ID + "=?", createArgs(id));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void update(Workout workout) {
        ContentValues cv = createCV(workout);
        updateInDb(cv, WorkoutContract._ID + "=?", createArgs(workout.getId()));
    }

    private ContentValues createCV(Workout workout){
        ContentValues cv = new ContentValues();
        cv.put(WorkoutContract._ID, workout.getId());
        cv.put(WORKOUT_NAME, workout.getName());
        cv.put(WORKOUT_ENDED, workout.getWorkoutEnded().toString());
        cv.put(WORKOUT_STARTED, workout.getWorkoutStarted().toString());
        cv.put(PRESET, workout.isPreset());

        return cv;
    }

    @Override
    public void delete(Workout workout) {
        removeFromDb(WorkoutContract._ID + "=?", createArgs(workout.getId()));
    }

    @Override
    public String save(Workout workout) {
        ContentValues cv = createCV(workout);
        saveToDb(cv);
        String id = getIdOfLastInsertedRow();
        List<Exercise> exercises = workout.getExList();
        addWorkoutIdToExercises(exercises, id);
        exerciseDAO.saveMany(exercises);
        return id;
    }

    private void addWorkoutIdToExercises(List<Exercise> exercises, String id){
        for(Exercise e : exercises){
            e.setWorkoutId(id);
        }
    }

    @Override
    public void initPresets() {
        throw new RuntimeException("not implemented");
    }

    public void clearWholeDatabase(){
        SQLiteDatabase db = writableDB();
        db.delete(TABLE_WORKOUT, null, null);
        db.delete(TABLE_EXERCISE, null, null);
        db.delete(TABLE_EXERCISETYPE, null, null);
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}

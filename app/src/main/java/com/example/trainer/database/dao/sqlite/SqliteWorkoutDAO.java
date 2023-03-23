package com.example.trainer.database.dao.sqlite;

import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.PRESET;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_ENDED;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_NAME;
import static com.example.trainer.database.contracts.WorkoutContract.WorkoutEntry.WORKOUT_STARTED;

import android.content.ContentValues;

import com.example.trainer.database.dao.entityCreators.EntityCreator;
import com.example.trainer.database.dao.framework.DAOBase;
import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.Workout;

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
        List<Workout> result = selectFromDb(clause, createArgs(1));
        return result;
    }

    @Override
    public List<Workout> getNonPresets() {
        String clause = String.format("%s=?", PRESET);
        return selectFromDb(clause, createArgs(0));
    }

    @Override
    public List<Workout> getAll() {
        return selectFromDb(null, null);
    }

    @Override
    public Workout getById(int id) {
        List<Workout> result = selectFromDb("_id=?", createArgs(id));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void update(Workout workout) {
        ContentValues cv = createCV(workout);
        updateInDb(cv, "_id=?", createArgs(workout.getId()));
    }

    private ContentValues createCV(Workout workout){
        ContentValues cv = new ContentValues();
        cv.put(WORKOUT_NAME, workout.getName());
        cv.put(WORKOUT_ENDED, workout.getWorkoutEnded().toString());
        cv.put(WORKOUT_STARTED, workout.getWorkoutStarted().toString());
        cv.put(PRESET, workout.isPreset());

        return cv;
    }

    @Override
    public void delete(Workout workout) {
        removeFromDb("_id=?", createArgs(workout.getId()));
    }

    @Override
    public int save(Workout workout) {
        ContentValues cv = createCV(workout);
        saveToDb(cv);
        int id = getIdOfLastInsertedRow();
        List<Exercise> exercises = workout.getExList();
        addWorkoutIdToExercises(exercises, id);
        exerciseDAO.saveMany(exercises);
        return id;
    }

    private void addWorkoutIdToExercises(List<Exercise> exercises, int id){
        for(Exercise e : exercises){
            e.setWorkoutId(id);
        }
    }

    @Override
    public void initPresets() {
        throw new RuntimeException("not implemented");
    }
}

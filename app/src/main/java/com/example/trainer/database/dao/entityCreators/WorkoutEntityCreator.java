package com.example.trainer.database.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.database.dao.framework.IExerciseDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WorkoutEntityCreator implements EntityCreator<Workout> {

    private final IExerciseDAO exerciseDAO;

    public WorkoutEntityCreator(IExerciseDAO exerciseDAO) {
        this.exerciseDAO = Objects.requireNonNull(exerciseDAO);
    }

    @Override
    public Workout createFrom(Cursor cursor) {
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
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        workout.setUserId(user);
        workout.setId(id);
        List<Exercise> exerciseList = exerciseDAO.getByWorkoutId(workout.getId());
        workout.setExList(exerciseList);
        return workout;
    }

    private Date parseDate(String target){
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        try {
            return df.parse(target);
        } catch (ParseException e){
            return new Date();
        }
    }
}

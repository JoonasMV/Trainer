package com.example.trainer.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.dao.framework.ISetDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;


import java.util.List;
import java.util.Objects;

public class ExerciseEntityCreator implements EntityCreator<Exercise> {

    private final ISetDAO setDAO;

    public ExerciseEntityCreator(ISetDAO setDAO){
       this.setDAO = Objects.requireNonNull(setDAO);
    }
    @Override
    public Exercise createFrom(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        int typeId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("typeId"));
        int workoutId = (int) cursor.getLong(cursor.getColumnIndexOrThrow("workoutId"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        List<ExerciseSet> sets = setDAO.getSetsByExerciseId(id);
        return new Exercise(name, id, workoutId, typeId, sets);
    }
}

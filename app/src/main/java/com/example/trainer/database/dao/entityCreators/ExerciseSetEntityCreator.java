package com.example.trainer.database.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.schemas.ExerciseSet;

public class ExerciseSetEntityCreator implements EntityCreator<ExerciseSet> {
    @Override
    public ExerciseSet createFrom(Cursor cursor) {
        ExerciseSet set = new ExerciseSet();
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        int amount = cursor.getInt(cursor.getColumnIndexOrThrow("reps"));
        double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("weight"));
        set.setReps(amount);
        set.setWeight(weight);
        set.setId(id);
        return set;
    }
}

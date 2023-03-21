package com.example.trainer.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.schemas.ExerciseSet;

public class ExerciseSetEntityCreator implements EntityCreator<ExerciseSet> {
    @Override
    public ExerciseSet createFrom(Cursor cursor) {
        ExerciseSet set = new ExerciseSet();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        int amount = cursor.getInt(cursor.getColumnIndexOrThrow("reps"));
        double weight = cursor.getDouble(cursor.getColumnIndexOrThrow("weight"));
        set.setAmount(amount);
        set.setWeight(weight);
        set.setId(id);
        return set;
    }
}

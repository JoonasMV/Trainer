package com.example.trainer.database.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.schemas.ExerciseType;

public class ExerciseTypeEntityCreator implements EntityCreator<ExerciseType>{

    public ExerciseTypeEntityCreator(){

    }
    @Override
    public ExerciseType createFrom(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow("exerciseTypeName"));
        ExerciseType exerciseType = new ExerciseType(name);
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        exerciseType.setId(id);
        return exerciseType;
    }
}

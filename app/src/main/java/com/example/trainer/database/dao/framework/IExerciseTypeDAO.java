package com.example.trainer.database.dao.framework;

import com.example.trainer.schemas.ExerciseType;

import java.util.List;

public interface IExerciseTypeDAO {
    String save(ExerciseType exerciseType);
    void deleteById(String id);
    List<ExerciseType> getAll();
    ExerciseType getExerciseTypeById(String id);
    ExerciseType getExerciseTypeByName(String name);
}

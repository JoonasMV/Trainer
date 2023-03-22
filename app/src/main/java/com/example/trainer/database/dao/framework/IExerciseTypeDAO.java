package com.example.trainer.database.dao.framework;

import com.example.trainer.schemas.ExerciseType;

import java.util.List;

public interface IExerciseTypeDAO {
    int save(ExerciseType exerciseType);
    void deleteById(int id);
    List<ExerciseType> getAll();
    ExerciseType getExerciseTypeById(int id);
    ExerciseType getExerciseTypeByName(String name);
}

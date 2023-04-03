package com.example.trainer.api;

import com.example.trainer.schemas.ExerciseType;

import java.util.List;

public interface ExerciseTypeOperations {

    List<ExerciseType> getAllExerciseTypes();

    void deleteExerciseType(String id);
}

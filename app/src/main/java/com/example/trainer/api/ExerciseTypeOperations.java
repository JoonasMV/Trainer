package com.example.trainer.api;

import com.example.trainer.model.ExerciseType;

import java.util.List;

public interface ExerciseTypeOperations {

    List<ExerciseType> getAllExerciseTypes();

    void deleteExerciseType(String id);

    ExerciseType saveExerciseType(ExerciseType exerciseType);
}

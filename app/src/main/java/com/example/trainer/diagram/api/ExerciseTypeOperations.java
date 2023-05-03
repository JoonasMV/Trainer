package com.example.trainer.diagram.api;

import com.example.trainer.diagram.api.model.ExerciseType;

import java.util.List;

/**
 * Includes all operations required for handling exercise types
 */
public interface ExerciseTypeOperations {

    /**
     * Gets all exercise types.
     * @return List<ExerciseType> list of exercise types
     */
    List<ExerciseType> getAllExerciseTypes();

    /**
     * Deletes exercise type with given id
     * @param id id of exercise type to be deleted
     */
    void deleteExerciseType(String id);

    /**
     * Saves exercise type
     * @param exerciseType exercise type to be saved
     * @return ExerciseType saved exercise type
     */
    ExerciseType saveExerciseType(ExerciseType exerciseType);
}

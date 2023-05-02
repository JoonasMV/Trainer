package com.example.trainer.api;

/**
 * Abstract class that is used to combine all operations. Can also be used for mocking.
 */
public abstract class API implements UserOperations, ExerciseTypeOperations,
        WorkoutOperations, QuoteOperations {
}

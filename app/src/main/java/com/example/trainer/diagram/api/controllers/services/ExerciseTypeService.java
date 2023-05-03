package com.example.trainer.diagram.api.controllers.services;

import android.util.Log;

import com.example.trainer.diagram.api.ExerciseTypeOperations;
import com.example.trainer.diagram.api.model.ExerciseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles exercise type caching and fetching from api. All methods except fetchOnBackground are synchronous.
 */
public class ExerciseTypeService {

    /**
     * List of exercise types.
     */
    private List<ExerciseType> exerciseTypes = new ArrayList<>();
    private final ExerciseTypeOperations api;

    public ExerciseTypeService(ExerciseTypeOperations api){
        this.api = api;
    }

    /**
     * Gets all exercise types. If exercise types are not cached, fetches them from api.
     * @return List of exercise types.
     */
    public List<ExerciseType> getAll() {
        if(exerciseTypes.isEmpty()){
            exerciseTypes = api.getAllExerciseTypes();
        }
        return exerciseTypes;
    }

    /**
     * Deletes exercise type with given id.
     * @param id Id of exercise type to delete.
     */
    public void deleteExerciseType(String id) {
        api.deleteExerciseType(id);
        filterOutId(id);
    }

    private void filterOutId(String id){
        this.exerciseTypes = exerciseTypes
                .stream()
                .filter(type -> !type.getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Checks if exercise type with given name exists.
     * @param name Name of exercise type to check.
     * @return True if exercise type with given name exists, false otherwise.
     */
    public boolean exerciseTypeExists(String name) {
        if(exerciseTypes.isEmpty()){
            exerciseTypes = api.getAllExerciseTypes();
        }
        Optional<ExerciseType> found = exerciseTypes
                .stream()
                .filter(type -> type.getName().equals(name))
                .findFirst();
        return found.isPresent();
    }

    /**
     * Creates new exercise type. The exercise type is saved if the api call is successful.
     * @param exerciseType Exercise type to create.
     */
    public void createExerciseType(ExerciseType exerciseType){
        if(exerciseTypes == null){
            exerciseTypes = api.getAllExerciseTypes();
        }
        ExerciseType saved = api.saveExerciseType(exerciseType);
        if(saved != null){
            exerciseTypes.add(saved);
        }
    }

    /**
     * Fetches exercise types from api on background.
     */
    public void fetchOnBackground(){
        Runnable runnable = () -> {
            Log.d("ExerciseTypeService", "Fetching on background...");
            exerciseTypes = api.getAllExerciseTypes();
            Log.d("ExerciseTypeService", "Fetched exercise types");
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}

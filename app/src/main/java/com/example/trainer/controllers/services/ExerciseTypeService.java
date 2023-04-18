package com.example.trainer.controllers.services;

import android.util.Log;

import com.example.trainer.api.ExerciseTypeOperations;
import com.example.trainer.model.ExerciseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExerciseTypeService {

    private List<ExerciseType> exerciseTypes = new ArrayList<>();
    private final ExerciseTypeOperations api;

    public ExerciseTypeService(ExerciseTypeOperations api){
        this.api = api;
    }

    public List<ExerciseType> getAll() {
        if(exerciseTypes.isEmpty()){
            exerciseTypes = api.getAllExerciseTypes();
        }
        return  exerciseTypes;
    }

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

    public void createExerciseType(ExerciseType exerciseType){
        if(exerciseTypes == null){
            exerciseTypes = api.getAllExerciseTypes();
        }
        ExerciseType saved = api.saveExerciseType(exerciseType);
        if(saved != null){
            exerciseTypes.add(saved);
        }
    }

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

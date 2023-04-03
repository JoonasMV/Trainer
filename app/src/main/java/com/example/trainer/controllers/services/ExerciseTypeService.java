package com.example.trainer.controllers.services;

import com.example.trainer.api.ExerciseTypeOperations;
import com.example.trainer.schemas.ExerciseType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExerciseTypeService {

    private List<ExerciseType> exerciseTypes;
    private final ExerciseTypeOperations api;

    public ExerciseTypeService(ExerciseTypeOperations api){
        this.api = api;
    }

    public List<ExerciseType> getAll() {
        if(exerciseTypes == null){
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
                .filter(type -> !type.get_id().equals(id))
                .collect(Collectors.toList());
    }

    public boolean exerciseTypeExists(String name) {
        if(exerciseTypes == null){
            exerciseTypes = api.getAllExerciseTypes();
        }
        Optional<ExerciseType> found = exerciseTypes
                .stream()
                .filter(type -> type.getExerciseTypeName().equals(name))
                .findFirst();
        return found.isPresent();
    }

    public void createExerciseType(ExerciseType exerciseType){
        if(exerciseTypes == null){
            exerciseTypes = api.getAllExerciseTypes();
        }

    }
}

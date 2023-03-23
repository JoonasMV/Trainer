package com.example.trainer.user_interface.exercises;


import com.example.trainer.schemas.ExerciseType;


public class ExerciseManager {

    private static ExerciseManager instance;

    private ExerciseManager(){}

    public static ExerciseManager getInstance() {
        if(instance == null) {
            instance = new ExerciseManager();
        }

        return instance;
    }
    private ExerciseType exerciseType;

    public void setExerciseType(ExerciseType et){
        exerciseType = et;
    }
    public ExerciseType getExerciseType(){
        return exerciseType;
    }
}

package com.example.trainer.database.dao.framework;

import com.example.trainer.schemas.Exercise;

import java.util.List;

public interface IExerciseDAO {

    String save(Exercise exercise);
    Exercise getById(String id);
    void saveMany(List<Exercise> exerciseList);
    void delete(Exercise exercise);
    void update(Exercise exercise);
    List<Exercise> getByWorkoutId(String id);


}

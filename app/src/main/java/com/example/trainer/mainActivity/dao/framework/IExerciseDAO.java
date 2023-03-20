package com.example.trainer.mainActivity.dao.framework;

import com.example.trainer.schemas.Exercise;

import java.util.List;

public interface IExerciseDAO {

    int save(Exercise exercise);
    Exercise getById(int id);
    void saveMany(List<Exercise> exerciseList);
    void delete(Exercise exercise);
    void update(Exercise exercise);
    List<Exercise> getByWorkoutId(int id);


}

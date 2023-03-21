package com.example.trainer.mainActivity.dao.framework;

import com.example.trainer.schemas.ExerciseSet;

import java.util.List;

public interface ISetDAO {

    void saveMany(List<ExerciseSet> sets, int exerciseId);
    List<ExerciseSet> getSetsByExerciseId(int id);

    void deleteAllSetsFromExercise(int id);

    void update(ExerciseSet set, int exerciseId);

}

package com.example.trainer.database.dao.framework;

import com.example.trainer.schemas.ExerciseSet;

import java.util.List;

public interface ISetDAO {

    void saveMany(List<ExerciseSet> sets, String exerciseId);
    List<ExerciseSet> getSetsByExerciseId(String id);

    void deleteAllSetsFromExercise(String id);

    void update(ExerciseSet set, String exerciseId);

}

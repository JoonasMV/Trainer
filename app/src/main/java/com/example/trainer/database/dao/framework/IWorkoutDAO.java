package com.example.trainer.database.dao.framework;

import com.example.trainer.schemas.Workout;

import java.util.List;

public interface IWorkoutDAO {
    List<Workout> getPresets();

    List<Workout> getNonPresets();

    List<Workout> getAll();

    Workout getById(String id);

    void update(Workout workout);

    void delete(Workout workout);

    String save(Workout workout);

    void initPresets();
}

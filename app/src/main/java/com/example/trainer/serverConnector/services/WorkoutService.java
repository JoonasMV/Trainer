package com.example.trainer.serverConnector.services;

import com.example.trainer.model.Workout;
import com.example.trainer.util.Settings;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WorkoutService extends BaseService<Workout> {
    private final String URI_PATH = Settings.DB_URI + "/workouts";

    private static WorkoutService instance = null;

    private WorkoutService() {}

    public static WorkoutService getInstance() {
        if(instance == null) instance = new WorkoutService();
        return instance;
    }

    @Override
    public Workout save(Workout item) {
        return save(item, URI_PATH);
    }

    @Override
    public Workout getByName(String name) {
        return null;
    }

    @Override
    public Workout getById(String id) {
        return getById(id, URI_PATH);
    }

    @Override
    public List<Workout> getAll() {
        return getAll(URI_PATH);
    }

    @Override
    Type getListType() {
        return new TypeToken<List<Workout>>() {}.getType();
    }

    @Override
    Type getType() {
        return new TypeToken<Workout>() {}.getType();
    }
}

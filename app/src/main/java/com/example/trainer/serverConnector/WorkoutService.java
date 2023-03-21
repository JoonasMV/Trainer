package com.example.trainer.serverConnector;

import com.example.trainer.Settings;
import com.example.trainer.schemas.Workout;

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
    public Workout getById(String id) {
        return getById(id, URI_PATH);
    }

    @Override
    public List<Workout> getAll() {
        return getAll(URI_PATH);
    }
}

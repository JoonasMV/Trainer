package com.example.trainer.serverConnector.services;

import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.util.Settings;

import java.lang.reflect.Type;
import java.util.List;

public class ExerciseTypeService extends BaseService<ExerciseType> {
    private final String URI_PATH = Settings.DB_URI + "/exerciseType";

    private static ExerciseTypeService instance = null;

    private ExerciseTypeService() {}

    public static ExerciseTypeService getInstance() {
        if(instance == null) instance = new ExerciseTypeService();
        return instance;
    }

    @Override
    public ExerciseType save(ExerciseType item) {
        return save(item, URI_PATH);
    }

    @Override
    public ExerciseType getById(String id) {
        return getById(id, URI_PATH);
    }

    @Override
    public List<ExerciseType> getAll() {
        return getAll(URI_PATH);
    }

    @Override
    public ExerciseType getByName(String name) { return getByName(URI_PATH, name); }

    @Override
    Type getListType() {
        return null;
    }

    @Override
    Type getType() {
        return null;
    }

}

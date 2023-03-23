package com.example.trainer.serverConnector;

import com.example.trainer.schemas.User;
import com.example.trainer.util.Settings;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UserService extends BaseService<User> {
    private final String URI_PATH = Settings.DB_URI + "/users";

    private static UserService instance = null;

    private UserService() {}

    public static UserService getInstance() {
        if(instance == null) instance = new UserService();
        return instance;
    }

    @Override
    public User save(User item) {
        return save(item, URI_PATH);
    }

    @Override
    public User getById(String id) {
        return getById(id, URI_PATH);
    }

    @Override
    public List<User> getAll() {
        return getAll(URI_PATH);
    }

    @Override
    Type getListType() {
        return new TypeToken<List<User>>() {}.getType();
    }

    @Override
    Type getType() {
        return new TypeToken<User>() {}.getType();
    }
}


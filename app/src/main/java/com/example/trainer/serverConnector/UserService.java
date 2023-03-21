package com.example.trainer.serverConnector;

import com.example.trainer.Settings;
import com.example.trainer.schemas.User;

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
}

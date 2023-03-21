package com.example.trainer.onlineDatabase;

import okhttp3.OkHttpClient;

/**
 * Handles the requests and responses to database server
 */

public class DatabaseConnector  {
    private static DatabaseConnector instance = null;

    /** Client for HTTP requests */
    private final OkHttpClient okHttpClient = new OkHttpClient();

    /**  Current service which is used to access database */
    private DatabaseService service;

    /** Service used to access users in database */
    private final DatabaseService userService = UserService.getInstance();

    private DatabaseConnector() {}

    public static DatabaseConnector getInstance() {
        if(instance == null) instance = new DatabaseConnector();
        return instance;
    }

    /**
     *
     * @return Returns self for chaining commands
     */
    public DatabaseConnector user() {
        service = userService;
        return this;
    }

    /**
     *
     * @return Returns all objects from wanted service type
     *
     */
    @SuppressWarnings("unchecked")
    public <T> T getAll() {
        return (T) service.getAll(okHttpClient);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOne(int id) {
        return (T) service.getById(okHttpClient, id);
    }

    @SuppressWarnings("unchecked")
    public <T> T post(Object item) {
        return (T) service.post(okHttpClient, item);
    }
}

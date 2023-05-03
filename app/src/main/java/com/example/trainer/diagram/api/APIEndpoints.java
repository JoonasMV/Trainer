package com.example.trainer.diagram.api;

public final class APIEndpoints {

    /**
     * Base url for the api
     */
    public static final String BASE_URL = "https://trainer-api.fly.dev";

    /**
     * Url for the auth endpoint
     */
    public static final String AUTH_URL = BASE_URL + "/api/auth";

    /**
     * Url for user related actions
     */
    public static final String USER_URL = BASE_URL + "/api/user";

    /**
     * Url for fetching usernames
     */
    public static final String USERS_URL = BASE_URL + "/api/users";
}

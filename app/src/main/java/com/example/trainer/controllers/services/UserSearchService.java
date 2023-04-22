package com.example.trainer.controllers.services;

import com.example.trainer.api.UserOperations;
import com.example.trainer.model.User;

import java.util.List;

public class UserSearchService {

    private final UserOperations api;

    public UserSearchService(UserOperations api) { this.api = api; }

    public List<User> getAllUsers() { return api.getAllUsers(); }
}

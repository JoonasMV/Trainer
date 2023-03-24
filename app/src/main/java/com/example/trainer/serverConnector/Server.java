package com.example.trainer.serverConnector;

import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;
import com.example.trainer.serverConnector.services.DatabaseService;
import com.example.trainer.serverConnector.services.ExerciseTypeService;
import com.example.trainer.serverConnector.services.UserService;
import com.example.trainer.serverConnector.services.WorkoutService;

/**
 * Handles the requests and responses to database server
 */

public class Server {
    private static Server instance = null;

    /** Service used to access users in database */
    private final DatabaseService<User> userService = UserService.getInstance();
    private final DatabaseService<ExerciseType> exerciseTypeService = ExerciseTypeService.getInstance();
    private final DatabaseService<Workout> workoutService = WorkoutService.getInstance();

    private Server() {}

    public static Server getInstance() {
        if(instance == null) instance = new Server();
        return instance;
    }

    /**
     * Changes service type for user items
     * @return Returns Returns changed DatabaseConnector
     * */
    public DatabaseService<User> user() {
        return userService;
    }

    /**
     * Changes service type for user ExerciseType items
     * @return Returns changed DatabaseConnector
     * */
    public DatabaseService<ExerciseType> exerciseType() {
        return exerciseTypeService;
    }

    /**
     * Changes service type for user Workout items
     * @return Returns changed DatabaseConnector
     * */
    public DatabaseService<Workout> workout() { return workoutService; }
}

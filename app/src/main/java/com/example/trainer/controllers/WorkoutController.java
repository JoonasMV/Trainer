package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.api.API;
import com.example.trainer.api.TrainerAPIWrapper;
import com.example.trainer.controllers.services.ExerciseTypeService;
import com.example.trainer.controllers.services.UserService;
import com.example.trainer.controllers.services.WorkoutService;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.Date;
import java.util.List;

public class WorkoutController extends BaseController {

    private final WorkoutService workoutService;
    private final ExerciseTypeService exerciseTypeService;
    private final UserService userService;
    private static WorkoutController instance;

    private WorkoutController(Context context){
        API api = new TrainerAPIWrapper(context);
        this.workoutService = new WorkoutService(api);
        this.exerciseTypeService = new ExerciseTypeService(api);
        this.userService = new UserService(api);
    }

    // DO NOT USE, ONLY FOR TESTING
    public WorkoutController(API api){
        this.workoutService = new WorkoutService(api);
        this.exerciseTypeService = new ExerciseTypeService(api);
        this.userService = new UserService(api);
    }

    public static void initController(Context context){
        instance = new WorkoutController(context);
    }

    public static WorkoutController getInstance(){
        if(instance == null){
            throw new RuntimeException("Controller not initialized");
        }
        return instance;
    }

    public static WorkoutController getInstance(Context context){
        if(instance == null){
            instance = new WorkoutController(context);
        }
        return instance;
    }

    @Override
    public void saveWorkout() {
        super.workout.setWorkoutEnded(new Date());
        workoutService.save(super.workout);
        super.workout = null;
    }

    @Override
    public List<ExerciseType> getExerciseTypes() {
        return exerciseTypeService.getAll();
    }

    @Override
    public void deleteExerciseType(String id) {
        exerciseTypeService.deleteExerciseType(id);
    }

    @Override
    public User findUser() {
        return userService.getUser();
    }

    @Override
    public List<Workout> getPresetWorkouts() {
        return workoutService.getPresetWorkouts();
    }

    @Override
    public List<Workout> getNonPresetWorkouts() {
        return workoutService.getNonPresetWorkouts();
    }

    @Override
    public void deleteWorkout(Workout workout) {
        workoutService.deleteWorkout(workout);
    }

    @Override
    public boolean exerciseTypeExists(String name) {
        return exerciseTypeService.exerciseTypeExists(name);
    }

    @Override
    public void createExerciseType(ExerciseType type) {
        exerciseTypeService.createExerciseType(type);
    }

    @Override
    public void makePreset(Workout workout) {
        workoutService.makePreset(workout);
    }

    @Override
    public void registerUser(User user) {
        userService.register(user);
    }

    @Override
    public void authenticateUser(User user) {
        userService.authenticate(user);
    }

    @Override
    public boolean sessionValid() {
        return userService.sessionValid();
    }

    @Override
    public void refreshSession() {
        userService.refresh();
    }

    @Override
    public void fetchWorkoutsAndExerciseTypesOnBackground() {
        workoutService.fetchOnBackground();
        exerciseTypeService.fetchOnBackground();
    }
}

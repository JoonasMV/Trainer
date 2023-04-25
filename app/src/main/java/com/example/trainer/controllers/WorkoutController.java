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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WorkoutController extends BaseController {

    private final WorkoutService workoutService;
    private final ExerciseTypeService exerciseTypeService;
    private final UserService userService;
    private static WorkoutController instance;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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
        Workout copy = new Workout(super.workout);
        executor.submit(() -> workoutService.save(copy));
        super.workout = null;
    }

    @Override
    public List<ExerciseType> getExerciseTypes() {
        return exerciseTypeService.getAll();
    }

    @Override
    public void deleteExerciseType(String id) {
        executor.submit(() -> exerciseTypeService.deleteExerciseType(id));
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
    public Future<List<Workout>> getPresetWorkoutsAsync() {
        return executor.submit(workoutService::getPresetWorkouts);
    }

    @Override
    public Future<List<Workout>> getSharedWorkoutsAsync(String username) {
        return executor.submit(() -> workoutService.getSharedWorkouts(username));
    }

    @Override
    public Future<List<Workout>> getNonPresetWorkoutsAsync() {
        return executor.submit(workoutService::getNonPresetWorkouts);
    }

    @Override
    public void deleteWorkout(Workout workout) {
        executor.submit(() -> workoutService.deleteWorkout(workout));
    }

    @Override
    public boolean exerciseTypeExists(String name) {
        return exerciseTypeService.exerciseTypeExists(name);
    }

    @Override
    public void createExerciseType(ExerciseType type) {
        executor.submit(() -> exerciseTypeService.createExerciseType(type));
    }

    @Override
    public void makePreset(Workout workout) {
        executor.submit(() -> workoutService.makePreset(workout));
    }

    @Override
    public void registerUser(User user) {
        executor.submit(() -> userService.register(user));
    }

    @Override
    public void authenticateUser(User user) {
        executor.submit(() -> userService.authenticate(user));
    }

    @Override
    public boolean sessionValid() {
        return userService.sessionValid();
    }

    @Override
    public void refreshSession() {
        executor.submit(userService::refresh);
    }

    @Override
    public void fetchWorkoutsAndExerciseTypesOnBackground() {
        exerciseTypeService.fetchOnBackground();
        workoutService.fetchOnBackground();
    }

    @Override
    public List<String> getUsernames() {
       return userService.getUsernames();
    }

    @Override
    public Future<List<ExerciseType>> getExerciseTypesAsync() {
        return executor.submit(exerciseTypeService::getAll);
    }
}

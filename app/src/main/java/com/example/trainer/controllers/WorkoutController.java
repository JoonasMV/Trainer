package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.api.API;
import com.example.trainer.api.TrainerAPIWrapper;
import com.example.trainer.controllers.services.ExerciseTypeService;
import com.example.trainer.controllers.services.QuoteService;
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

/**
 * Controller for all app related functionality
 */
public class WorkoutController extends BaseController {

    /**
     * Service for handling workout related functionality
     */
    private final WorkoutService workoutService;
    /**
     * Service for handling exercise type related functionality
     */
    private final ExerciseTypeService exerciseTypeService;

    /**
     * Service for handling user related functionality
     */
    private final UserService userService;

    /**
     * Service for handling quote related functionality
     */
    private final QuoteService quoteService;

    private static WorkoutController instance;
    /**
     * Executor for running tasks in the background
     */
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private WorkoutController(Context context){
        API api = new TrainerAPIWrapper(context);
        this.workoutService = new WorkoutService(api);
        this.exerciseTypeService = new ExerciseTypeService(api);
        this.userService = new UserService(api);
        this.quoteService = new QuoteService(api);
    }

    // DO NOT USE, ONLY FOR TESTING
    public WorkoutController(API api){
        this.workoutService = new WorkoutService(api);
        this.exerciseTypeService = new ExerciseTypeService(api);
        this.userService = new UserService(api);
        this.quoteService = new QuoteService(api);
    }

    /**
     * Initializes the controller, intended to be called on app startup. This allows us to call getInstance() without
     * any parameters.
     * @param context the context of the application
     */
    public static void initController(Context context){
        instance = new WorkoutController(context);
    }

    /**
     * Gets the instance of the controller, can only be called after initController()
     * @return the instance of the controller
     */
    public static WorkoutController getInstance(){
        if(instance == null){
            throw new RuntimeException("Controller not initialized");
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveWorkout() {
        super.workout.setWorkoutEnded(new Date());
        Workout copy = new Workout(super.workout);
        executor.submit(() -> workoutService.save(copy));
        super.workout = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveWorkout(Workout workout) {
        executor.submit(() -> workoutService.save(workout));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ExerciseType> getExerciseTypes() {
        return exerciseTypeService.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteExerciseType(String id) {
        executor.submit(() -> exerciseTypeService.deleteExerciseType(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUser() {
        return userService.getUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Workout> getPresetWorkouts() {
        return workoutService.getPresetWorkouts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Workout> getNonPresetWorkouts() {
        return workoutService.getNonPresetWorkouts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<List<Workout>> getPresetWorkoutsAsync() {
        return executor.submit(workoutService::getPresetWorkouts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<List<Workout>> getSharedWorkoutsAsync(String username) {
        return executor.submit(() -> workoutService.getSharedWorkouts(username));
    }

    @Override
    public void logOut() {
        userService.logOut();
    }

    @Override
    public Future<List<Workout>> getNonPresetWorkoutsAsync() {
        return executor.submit(workoutService::getNonPresetWorkouts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkout(Workout workout) {
        executor.submit(() -> workoutService.deleteWorkout(workout));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exerciseTypeExists(String name) {
        return exerciseTypeService.exerciseTypeExists(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createExerciseType(ExerciseType type) {
        executor.submit(() -> exerciseTypeService.createExerciseType(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makePreset(Workout workout) {
        executor.submit(() -> workoutService.makePreset(workout));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<Boolean> registerUserAsync(User user) {
        return executor.submit(() -> userService.register(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<Boolean> authenticateUserAsync(User user) {
        return executor.submit(() -> userService.authenticate(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sessionValid() {
        return userService.sessionValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshSession() {
        executor.submit(userService::refresh);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetchWorkoutsAndExerciseTypesOnBackground() {
        exerciseTypeService.fetchOnBackground();
        workoutService.fetchOnBackground();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUsernames() {
       return userService.getUsernames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<List<ExerciseType>> getExerciseTypesAsync() {
        return executor.submit(exerciseTypeService::getAll);
    }

    @Override
    public List<String> getQuotes() {
        return quoteService.getQuotes();
    }

    @Override
    public void makeShared(Workout workout){
        executor.submit(() -> workoutService.makeShared(workout));
    }
}

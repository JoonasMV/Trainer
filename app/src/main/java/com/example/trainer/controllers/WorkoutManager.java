package com.example.trainer.controllers;

import android.content.Context;

import com.example.trainer.database.dao.framework.DAOFactory;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.database.dao.sqlite.BetterSqliteDAOFactory;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.WorkoutSerializer;

import java.util.Date;
import java.util.List;

public class WorkoutManager extends BaseController{

    private static WorkoutManager instance;

    private Workout workout;
    private final IWorkoutDAO workoutDAO;

    private final IUserDAO userDAO;

    private final IExerciseTypeDAO exerciseTypeDAO;


    private WorkoutManager(DAOFactory factory){
        this.workoutDAO = factory.createWorkoutDAO();
        this.userDAO = factory.createUserDAO();
        this.exerciseTypeDAO = factory.createExerciseTypeDAO();
    }

    public static TrainerController getInstance() {
        if(instance == null) {
            instance = new WorkoutManager(new BetterSqliteDAOFactory());
        }

        return instance;
    }

    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

    public void cancelWorkout(Context context) {

        this.workout = null;
        WorkoutSerializer.clearPrefs(context);
    }

    public void saveWorkout() {
        this.workout.setWorkoutEnded(new Date());
        workoutDAO.save(workout);
        this.workout = null;

    }
    public List<ExerciseType> getExerciseTypes(){
        return exerciseTypeDAO.getAll();
    }

    public void deleteExerciseType(int id){
       exerciseTypeDAO.deleteById(id);
    }

    public void createUser(User user){
        this.userDAO.createUser(user);
    }

    public User findUser(){
        return userDAO.getUser();
    }

    public List<Workout> getPresetWorkouts(){
        return workoutDAO.getPresets();
    }
    public List<Workout> getNonPresetWorkouts(){
        return workoutDAO.getNonPresets();
    }

    @Override
    public void makePreset(Workout workout){
        workout.setPreset(true);
        System.out.println("making preset" + workout.getName());
        workoutDAO.save(workout);
    }

    @Override
    public Workout getWorkout() {
       return workout;
    }

    @Override
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void deleteWorkout(Workout workout){
        workoutDAO.delete(workout);
    }

    public boolean exerciseTypeExists(String type){
       String caseInsensitive = type.toLowerCase();
       String found = exerciseTypeDAO.getExerciseTypeByName(type).getName().toLowerCase();
       return caseInsensitive.equals(found);
    }

    public void createExerciseType(ExerciseType type){
        exerciseTypeDAO.save(type);
    }
}


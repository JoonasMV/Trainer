package com.example.trainer.controllers;

import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class APIWorkoutManager extends BaseController{

    private IWorkoutDAO workoutDAO;
    private IUserDAO userDAO;
    private IExerciseTypeDAO exerciseTypeDAO;

    private List<Workout> workouts;
    private List<ExerciseType> exerciseTypes;

    private User user;

    @Override
    public void saveWorkout() {
        workoutDAO.save(workout);
        workouts.add(workout);
        super.workout = null;
    }

    @Override
    public List<ExerciseType> getExerciseTypes() {
        if(exerciseTypes == null){
            exerciseTypes = exerciseTypeDAO.getAll();
        }
        return exerciseTypes;
    }

    @Override
    public void deleteExerciseType(String id) {
        exerciseTypeDAO.deleteById(id);
        exerciseTypes = exerciseTypes
                .stream()
                .filter(type -> !type.getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public User findUser() {
        return null;
    }

    @Override
    public List<Workout> getPresetWorkouts() {
        return workouts
                .stream()
                .filter(Workout::preset)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workout> getNonPresetWorkouts() {
        return workouts
                .stream()
                .filter(workout -> !workout.preset())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkout(Workout workout) {
        workoutDAO.delete(workout);
        workouts = workouts
                .stream()
                .filter(workout1 -> !workout1.getId().equals(workout.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean exerciseTypeExists(String name) {
        Optional<ExerciseType> result = exerciseTypes.stream()
                .filter(type -> type.getName().equals(name))
                .findFirst();
        return result.isPresent();
    }

    @Override
    public void createExerciseType(ExerciseType type) {
        exerciseTypeDAO.save(type);
        exerciseTypes.add(type);
    }

    @Override
    public void makePreset(Workout workout) {
        workout.setPreset(true);
        workoutDAO.save(workout);
    }
}

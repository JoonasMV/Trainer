package com.example.trainer.mock;

import com.example.trainer.diagram.api.API;
import com.example.trainer.diagram.api.model.ExerciseType;
import com.example.trainer.diagram.api.model.User;
import com.example.trainer.diagram.api.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class MockAPI extends API {

    private Object recentParam;
    @Override
    public boolean registerUser(User user) {
        recentParam = user;
        return false;
    }

    @Override
    public boolean authenticateUser(User user) {
        recentParam = user;
        return false;
    }

    @Override
    public void refreshToken() {
    }

    @Override
    public User getUser() {
        return new User("test", "password");
    }

    @Override
    public boolean sessionValid() {
       return true;
    }

    @Override
    public List<String> getUsernames() {
        return null;
    }

    @Override
    public void logOut() {

    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        List<ExerciseType> list = new ArrayList<>();
        list.add(new ExerciseType("test"));
        return list;
    }

    @Override
    public void deleteExerciseType(String id) {
        recentParam = id;
    }

    @Override
    public ExerciseType saveExerciseType(ExerciseType exerciseType) {
        recentParam = exerciseType;
        return exerciseType;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        recentParam = workout;
        return workout;
    }

    @Override
    public List<Workout> getWorkouts() {
        List<Workout> list = new ArrayList<>();
        list.add(new Workout("name"));
        Workout preset = new Workout("preset");
        preset.setPreset(true);
        list.add(preset);
        return list;
    }

    @Override
    public void deleteWorkout(String id) {
        recentParam = id;
    }

    @Override
    public void updateWorkout(Workout workout) {

    }

    @Override
    public List<Workout> getSharedWorkouts(String username) {
        return null;
    }

    public Object getRecentParam(){
        return this.recentParam;
    }

    @Override
    public String getQuotes() {
        return null;
    }
}

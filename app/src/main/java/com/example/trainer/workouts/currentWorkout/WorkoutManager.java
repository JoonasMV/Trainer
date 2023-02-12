package com.example.trainer.workouts.currentWorkout;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutManager {

    private static WorkoutManager instance;

    private Workout workout = null;
    ExerciseDAO exerciseDAO = new ExerciseDAO();
    WorkoutDAO workoutDAO = new WorkoutDAO();

    private WorkoutManager(){}

    public static WorkoutManager getInstance() {
        if(instance == null) {
            instance = new WorkoutManager();
        }

        return instance;
    }

    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

    public void cancelWorkout() {
        this.workout = null;
    }

    public void saveWorkout() {
        this.workout.setWorkoutEnded(new Date());
        workoutDAO.add(workout);
        this.workout = null;
    }

    public boolean workoutActive() {
        return this.workout != null;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public void initWorkout(){

    }

    public Workout getWorkout()  {
        return this.workout;
    }

    public void addSet(int exercisePosition, ExerciseSet set){
       this.workout.getExList().get(exercisePosition).getSetList().add(set);
    }

    public void addExercise(Exercise exercise) {
        this.workout.addExerciseToList(exercise);
    }



}

package com.example.trainer.workouts.currentWorkout;

import android.content.Context;
import android.util.Log;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.util.WorkoutSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutManager {

    private static WorkoutManager instance;

    private Workout workout;
    ExerciseDAO exerciseDAO;
    WorkoutDAO workoutDAO;


    private WorkoutManager(){
        this.exerciseDAO = new ExerciseDAO();
        this.workoutDAO = new WorkoutDAO();
    }

    public static WorkoutManager getInstance() {
        if(instance == null) {
            instance = new WorkoutManager();
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
        workoutDAO.add(workout);
        this.workout = null;

    }

    public void setEndWorkout(){
        this.workout.setWorkoutEnded(new Date());
    }

    public void saveToDB(){
        workoutDAO.add(workout);
        this.workout = null;
    }

    public void changeWorkoutName(String name){
        if(!name.isEmpty()){
            workout.setName(name);
        }
    }

    public boolean workoutActive() {
        return this.workout != null;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public Workout getWorkout()  {
        return this.workout;
    }

    public void addSet(int exercisePosition){
       this.workout.getExList().get(exercisePosition).getSetList().add(new ExerciseSet());
    }

    public void addExercise(Exercise exercise) {
        if (workout == null) {
            this.workout = new Workout("Workout", new Date());
        }
        exercise.addSet(new ExerciseSet());
        workout.addExerciseToList(exercise);
    }

    public void saveToPref(Context context){
        Log.d("Serialization", "Serialized workout");
        WorkoutSerializer.writeWorkoutToPref(this.workout, context);
    }

    public void readFromPref(Context context){
        this.workout = WorkoutSerializer.readWorkoutFromPref(context);

        if(workout != null){
            Log.d("Serialization", String.format("Deserialized workout\n name: %s \n length of exercise list: %d", workout.getName(), workout.getExList().size()));
        }
    }

    public void startWorkoutFromPreset(Workout workout){
        Workout copy = new Workout(workout.getName(), new Date());
        copy.setExList(new ArrayList<>(workout.getExList()));
        copy.setPreset(false);
        this.workout = copy;
    }

}


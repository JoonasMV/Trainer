package com.example.trainer.workouts.currentWorkout;

import android.content.Context;
import android.util.Log;

import com.example.trainer.dao.framework.DAOFactory;
import com.example.trainer.dao.framework.IExerciseDAO;
import com.example.trainer.dao.framework.IExerciseTypeDAO;
import com.example.trainer.dao.framework.IUserDAO;
import com.example.trainer.dao.framework.IWorkoutDAO;
import com.example.trainer.dao.sqlite.BetterSqliteDAOFactory;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.WorkoutSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutManager {

    private static WorkoutManager instance;

    private Workout workout;
    private final IExerciseDAO exerciseDAO;
    private final IWorkoutDAO workoutDAO;

    private final IUserDAO userDAO;

    private final IExerciseTypeDAO exerciseTypeDAO;


    private WorkoutManager(DAOFactory factory){
        this.exerciseDAO = factory.createExerciseDAO();
        this.workoutDAO = factory.createWorkoutDAO();
        this.userDAO = factory.createUserDAO();
        this.exerciseTypeDAO = factory.createExerciseTypeDAO();
    }

    public static WorkoutManager getInstance() {
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
        System.out.println("Starting workout exlist size: " + workout.getExList().size());
        this.workout = copy;
    }

    public List<ExerciseType> getExerciseTypes(){
        return exerciseTypeDAO.getAll();
    };

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

    public void makePreset(Workout workout){
        workout.setPreset(true);
        System.out.println("making preset" + workout.getName());
        workoutDAO.save(workout);
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


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

    private final static WorkoutManager instance = new WorkoutManager();

    private Workout workout = null;
    ExerciseDAO exerciseDAO = new ExerciseDAO(null);
    WorkoutDAO workoutDAO = new WorkoutDAO(null);

    private WorkoutManager(){}

    public static WorkoutManager getInstance() {
        return instance;
    }

    public void startWorkout(String workoutName){
        this.workout = new Workout(workoutName, new Date());
    }

    public void cancelWorkout() {
        this.workout = null;
    }

    public void saveWorkout() {
        workoutDAO.add(workout);
    }

    public boolean workoutActive() {
        return this.workout != null;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
    }

    public void initWorkout(){
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        List setlist = new ArrayList<>();
        List setlist2 = new ArrayList<>();
        setlist.add(new ExerciseSet(100, 3));
        setlist.add(new ExerciseSet(105, 2));
        setlist2.add(new ExerciseSet(50, 10));
        setlist2.add(new ExerciseSet(25, 20));
        Exercise testEx1 = new Exercise("squat");
        Exercise testEx2 = new Exercise("bench");
        testEx1.setSetList(setlist);
        testEx2.setSetList(setlist2);
        exerciseList.add(testEx1);
        exerciseList.add(testEx2);

        Workout currentWorkout = new Workout(
                "test workout",
                new Date(),
                new Date()
        );
        currentWorkout.setExList(exerciseList);

        setWorkout(currentWorkout);
        System.out.println("WORKOUT INIT");
        System.out.println(this.workout.getName());
    }

    public Workout getWorkout()  {
        return this.workout;
    }




}

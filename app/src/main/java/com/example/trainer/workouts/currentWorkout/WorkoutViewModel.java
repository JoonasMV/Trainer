package com.example.trainer.workouts.currentWorkout;

import androidx.lifecycle.ViewModel;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Se vaan toimii ¯\_(ツ)_/¯
public class WorkoutViewModel extends ViewModel {

    SelectExercise selectExercise = new SelectExercise();
    CurrentWorkoutFragment currentWorkoutFragment = new CurrentWorkoutFragment();

    Workout currentWorkout;
    ExerciseDAO exerciseDAO = new ExerciseDAO(currentWorkoutFragment.getContext());

    public WorkoutViewModel() {
        initWorkout();
    }

    public void testing() {
        System.out.println("Internal screaming");
    }

    public void addExerciseToWorkoutById(int id) {
        Exercise newExercise = exerciseDAO.getExerciseById(id);
        currentWorkout.addExerciseToList(newExercise);
    }

    //TODO: workout initiation needs to be pulled from DB
    public void initWorkout() {
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

        currentWorkout = new Workout(
                "test workout",
                new Date(),
                new Date()
        );
        currentWorkout.setExList(exerciseList);
    }

    public Workout getWorkout() {
        return currentWorkout;
    }
}

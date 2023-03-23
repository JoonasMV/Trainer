package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.legacyDAO.ExerciseDAO;
import com.example.trainer.database.legacyDAO.WorkoutDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;
import com.example.trainer.controllers.WorkoutManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class WorkoutDAOTests{
    private static Context ctx;

    private static WorkoutDAO wDao;

    private static ExerciseDAO eDao;

    private static int exerciseTypeId;

    private List<Exercise> exerciseList;

    private List<ExerciseSet> setList;

    private static WorkoutManager workoutManager;


    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        DatabaseHelper.initialize(ctx);
        workoutManager = WorkoutManager.getInstance();
        wDao = new WorkoutDAO();
        eDao = new ExerciseDAO();

    }

    @Before
    public void beforeEach(){
        wDao.deleteAllWorkouts();
        eDao.deleteAllExerciseTypes();

        exerciseTypeId = eDao.addExerciseType(new ExerciseType("mockType"));

        exerciseList = new ArrayList<>();

        setList = new ArrayList<>();


        Exercise e1 = new Exercise(exerciseTypeId);
        Exercise e2 = new Exercise(exerciseTypeId);
        Exercise e3 = new Exercise(exerciseTypeId);

        ExerciseSet s1 = new ExerciseSet(20, 100);
        ExerciseSet s2 = new ExerciseSet(3, 50);
        ExerciseSet s3 = new ExerciseSet(9, 133);
        ExerciseSet s4 = new ExerciseSet(28, 11);

        setList.add(s1);
        setList.add(s2);
        setList.add(s3);
        setList.add(s4);

        e1.setSetList(setList);
        e2.addSet(s2);
        e2.addSet(s3);
        e3.addSet(s4);
        e3.addSet(s3);

        exerciseList.add(e1);
        exerciseList.add(e2);
        exerciseList.add(e3);



    }

    @Test
    public void workout_add() {

        Workout workout = new Workout("workout");
        workout.setExList(exerciseList);
        workout.setPreset(true);

        int id = wDao.save(workout);

        Workout workout1 = wDao.getById(id);

        assertEquals(id, workout1.getId());
    }



}


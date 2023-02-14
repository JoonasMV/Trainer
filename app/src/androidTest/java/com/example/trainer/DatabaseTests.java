package com.example.trainer;

import android.content.Context;

import androidx.annotation.DisplayContext;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.SetDAO;
import com.example.trainer.database.dao.UserDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    private static Context ctx;

    private static DatabaseHelper dbConnection;
    private static ExerciseDAO exDao;
    private static WorkoutDAO workoutDAO;
    private static SetDAO setDAO;
    private static UserDAO userDAO;

    private static List<ExerciseSet> mockSets;
    private static List<Exercise> mockExercises;
    private static List<Workout> mockWorkouts;

    private static WorkoutManager workoutManager;

    private static int exTypeId;

    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        DatabaseHelper.initialize(ctx);
        workoutManager = WorkoutManager.getInstance();
        exDao = new ExerciseDAO();
        workoutDAO = new WorkoutDAO();
        setDAO = new SetDAO();
        userDAO = new UserDAO();

    }

    @Before
    public void beforeTest(){
        exDao.deleteAllExercises();
        workoutDAO.deleteAllWorkouts();
        setDAO.deleteAllSets();
        exDao.deleteAllExerciseTypes();
        exTypeId = exDao.addExerciseType(new ExerciseType("mockType"));

        workoutManager.startWorkout("new workout");

        Exercise e = new Exercise(exTypeId);

        workoutManager.addExercise(e);

        workoutManager.addSet(0);

        workoutManager.saveWorkout();
    }


    @Test
    public void workouts_are_saved(){
        Workout w = workoutDAO.getAll().get(0);

        assertEquals("new workout", w.getName());
        assertEquals(1, w.getExList().size());
    }

    @Test
    public void setCreationAndGet() {


    }

    @Test public void exerciseCreationAndGetById() {
        Exercise exercise = new Exercise(exTypeId);

        int id = exDao.addExercise(exercise);

        Exercise ex = exDao.getExerciseById(id);

        assertEquals("MOCKEXERCISE", ex.getExerciseName());
    }

    @Test
    public void exerciseGetByWorkoutId() {
        Exercise exercise = mockExercises.get(0);
        exercise.setWorkoutId(1);

        int id = exDao.addExercise(exercise);
        List<Exercise> listFromDb = exDao.getExerciseByWorkoutId(1);

        Exercise exFromDb = listFromDb.get(0);

        assertEquals(exercise.getExerciseName(), exFromDb.getExerciseName());
    }

    @Test
    public void excerciseByName() {
        Exercise exercise = mockExercises.get(0);
        mockExercises.add(exercise);
        exDao.addManyExercises(mockExercises);

        List<Exercise> listFromDb = exDao.getExercisesByName(exercise.getExerciseName());

        assertEquals(mockExercises.size(), listFromDb.size());

        Exercise exFromDb = listFromDb.get(0);

        assertEquals(exercise.getExerciseName(), exFromDb.getExerciseName());
    }

    @Test
    public void workoutCreationAndSet() {
        fail();
    }





}

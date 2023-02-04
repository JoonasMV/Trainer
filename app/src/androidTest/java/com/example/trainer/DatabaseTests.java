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
import com.example.trainer.database.schemas.Workout;

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

    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        exDao = new ExerciseDAO(ctx);
        workoutDAO = new WorkoutDAO(ctx);
        setDAO = new SetDAO(ctx);
        userDAO = new UserDAO(ctx);

    }

    @Before
    public void beforeTest(){
        exDao.deleteAllExercises();
        workoutDAO.deleteAllWorkouts();
        setDAO.deleteAllSets();

        mockSets = new ArrayList<>();
        mockSets.add(new ExerciseSet(20, 10));
        mockSets.add(new ExerciseSet(30, 6));

        mockExercises = new ArrayList<>();

        mockExercises.add(new Exercise("TEST", mockSets));

    }

    @Test
    public void setCreationAndGet() {
        long exId = 1;

        setDAO.addSetsToDb(mockSets, exId);
        List<ExerciseSet> setsFromDb = setDAO.getSetsByExerciseId((int) exId);

        assertEquals(setsFromDb.size(), mockSets.size());

        ExerciseSet mockSet1 = mockSets.get(0);
        ExerciseSet setFromDb = setsFromDb.get(0);

        assertEquals(mockSet1.getWeight(), setFromDb.getWeight(), 0);

    }

    @Test
    public void exerciseCreationAndGetById() {
        Exercise exercise = mockExercises.get(0);

        int id = exDao.addExercise(exercise);

        Exercise ex = exDao.getExerciseById(id);

        assertEquals(exercise.getExerciseName(), ex.getExerciseName());
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

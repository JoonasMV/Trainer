package com.example.trainer;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.mainActivity.dao.ExerciseDAO;
import com.example.trainer.mainActivity.dao.SetDAO;
import com.example.trainer.mainActivity.dao.UserDAO;
import com.example.trainer.mainActivity.dao.WorkoutDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import java.util.Date;
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
        exDao.deleteAll();
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
    public void get_presets_from_database(){
        List<Workout> list = workoutDAO.getPresets();

        assertEquals(0, list.size());
    }

    @Test
    public void get_non_presets_from_database(){
        List<Workout> list = workoutDAO.getNonPresets();

        assertEquals(1, list.size());
    }

    @Test
    public void can_create_and_save_workout(){
        workoutManager.startWorkout("mockWorkout");

        workoutManager.addExercise(new Exercise(exTypeId));

        workoutManager.addSet(0);

        Workout w = workoutManager.getWorkout();

        w.setWorkoutEnded(new Date());
        w.getExList().get(0).getSetList().get(0).setWeight(10);

        int id = workoutDAO.save(w);

        Workout workoutFromDb = workoutDAO.getById(id);


        assertEquals("mockWorkout", workoutFromDb.getName());

        ExerciseSet set = workoutFromDb.getExList().get(0).getSetList().get(0);

        assertEquals(10, set.getWeight(), 0);
    }





}

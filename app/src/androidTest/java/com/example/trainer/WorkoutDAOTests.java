package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class WorkoutDAOTests{
    private static Context ctx;

    private static WorkoutDAO wDao;

    private static ExerciseDAO eDao;

    private static int exerciseTypeId;

    private List<Exercise> exerciseList;

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


        Exercise e1 = new Exercise(exerciseTypeId));
        Exercise e2 = new Exercise(exerciseTypeId));
        Exercise e3 = new Exercise(exerciseTypeId));
    }

    @Test
    public void workout_creation() {
        exerciseTypeId = eDao.addExerciseType(new ExerciseType("mock2"));

        ExerciseType type = eDao.getExerciseTypeById(exerciseTypeId);

        assertEquals("mock2", type.getName());
    }

    @Test
    public void exercise_creation(){
        Exercise e = new Exercise(exerciseTypeId);

        int id = eDao.addExercise(e);


        assertNotEquals(-1, id);

        Exercise exFromDb = eDao.getExerciseById(id);

        assertEquals("mockType", exFromDb.getExerciseName());
    }

    @Test
    public void getByWorkoutId() {
        workoutManager.startWorkout("TEST");

        Exercise e = new Exercise(exerciseTypeId);

        workoutManager.addExercise(e);

        WorkoutDAO workoutDAO = new WorkoutDAO();

        Workout w = workoutManager.getWorkout();
        w.setWorkoutEnded(new Date());

        int id = workoutDAO.add(w);

        Log.d("WORKOUT ID", Integer.toString(id));

        List<Exercise> exFromDb = eDao.getExerciseByWorkoutId(id);

        assertEquals(1, exFromDb.size());
        assertEquals("mockType", exFromDb.get(0).getExerciseName());
    }
}


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

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ExerciseDAOTests{
    private static Context ctx;

    private static ExerciseDAO exDao;

    private static WorkoutManager workoutManager;

    private int exerciseTypeId;

    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        DatabaseHelper.initialize(ctx);
        workoutManager = WorkoutManager.getInstance();

        exDao = new ExerciseDAO();

    }

    @Before
    public void beforeEach(){
        exDao.deleteAllExercises();
        exDao.deleteAllExerciseTypes();

        exerciseTypeId = exDao.addExerciseType(new ExerciseType("mockType"));
    }

    @Test
    public void exerciseType_creation() {
        exerciseTypeId = exDao.addExerciseType(new ExerciseType("mock2"));

        ExerciseType type = exDao.getExerciseTypeById(exerciseTypeId);

        assertEquals("mock2", type.getName());
    }

    @Test
    public void exercise_creation(){
        Exercise e = new Exercise(exerciseTypeId);

        int id = exDao.addExercise(e);


        assertNotEquals(-1, id);

        Exercise exFromDb = exDao.getExerciseById(id);

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

        List<Exercise> exFromDb = exDao.getExerciseByWorkoutId(id);

        assertEquals(1, exFromDb.size());
        assertEquals("mockType", exFromDb.get(0).getExerciseName());
    }
}

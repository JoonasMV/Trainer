package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.Workout;
import com.example.trainer.controllers.WorkoutManager;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutManagerTest {

    private static WorkoutManager manager = WorkoutManager.getInstance();

    @Before
    public void beforeTest(){
        manager.cancelWorkout(null);
        manager.startWorkout("MOCK");
    }


    @Test
    public void workoutManager_startWorkout(){
        assertNotNull(manager.getWorkout());
    }

    @Test
    public void workoutManager_cancelWorkout(){
        manager.cancelWorkout(null);
        assertNull(manager.getWorkout());
    }

    @Test
    public void workoutManager_workOutActive() {
        assertTrue(manager.workoutActive());
    }

    @Test
    public void workoutManager_setWorkout() {
        manager.setWorkout(new Workout("new", new Date()));
        assertEquals("new", manager.getWorkout().getName());
    }

    @Test
    public void workoutManager_addExercise(){
        Exercise exercise = new Exercise("mock", 1, 1, 1, new ArrayList<>());
        manager.addExercise(exercise);

        assertEquals("mock", manager.getWorkout().getExList().get(0).getExerciseName());
    }

    @Test
    public void workoutManager_addSet() {
        Exercise exercise = new Exercise("mock", 1, 1, 1, new ArrayList<>());

        manager.addExercise(exercise);

        manager.addSet(0);

        assertEquals(2, exercise.getSetList().size());
    }


}

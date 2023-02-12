package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class WorkoutManagerTest {

    private static WorkoutManager manager = WorkoutManager.getInstance();

    @Before
    public void beforeTest(){
        manager.cancelWorkout();
        manager.startWorkout("MOCK");
    }


    @Test
    public void workoutManager_startWorkout(){
        assertNotNull(manager.getWorkout());
    }

    @Test
    public void workoutManager_cancelWorkout(){
        manager.cancelWorkout();
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
        manager.addExercise(new Exercise("TEST"));

        assertEquals("TEST", manager.getWorkout().getExList().get(0).getExerciseName());
    }

    @Test
    public void workoutManager_addSet() {
        manager.addExercise(new Exercise("TEST"));

        manager.addSet(0, new ExerciseSet(20, 20));

        assertEquals(20, manager.getWorkout().getExList().get(0).getSetList().get(0).getWeight(), 0);
    }

}

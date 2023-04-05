package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseSet;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class AppSchemasTest {
    private Exercise exercise;
    private ExerciseSet set;
    private ExerciseType type;
    private User user;
    private Workout workout;


    @Before
    public void init(){
//        exercise = new Exercise()
        exercise = new Exercise();
        exercise.setExerciseType(new ExerciseType("test"));
        exercise.setId("123");
        type = new ExerciseType("tyyppi");
        set = new ExerciseSet();
        user = new User("user");
        workout = new Workout("testWorkout");
        exercise.addSet(set);
        exercise.addSet(new ExerciseSet());
        exercise.addSet(new ExerciseSet());

    }


    //exercise type tests

    @Test
    public void exerciseTypeTests(){
        assertEquals("tyyppi", type.getName());
    }


    //exercise set tests

    @Test
    public void testGetWeight(){
        set.setWeight(40.4);
        assertEquals(40.4, set.getWeight(), 0);
    }

    @Test
    public void testGetAmount(){
        set.setReps(10);
        assertEquals(10, set.getReps(), 0);
    }
    @Test
    public void testGetId(){
        set.setId("3");
        assertEquals("3", set.getId());
    }


    @Test
    public void testToString(){
        set = new ExerciseSet();
        set.setId("1");
        set.setReps(5);
        set.setWeight(4);
        assertEquals("ExerciseSet{, weight=4.0, amount=5, id=1}", set.toString());
    }


    //Exercise tests
    @Test
    public void testGetExId(){
        exercise.setId("55");
        assertEquals("55", exercise.getId());
    }
    @Test
    public void testGetSetList(){
        ExerciseSet set1 = new ExerciseSet();
        set1.setWeight(44);
        set1.setReps(10);
        exercise.setSets(new ArrayList<>());
        exercise.addSet(set1);
        assertEquals(44, exercise.getSets().get(0).getWeight(), 0);
        assertEquals(10, exercise.getSets().get(0).getReps(), 0);
    }
    @Test
    public void testGetTypeId(){
        exercise.setExerciseType(new ExerciseType("jotain"));
        assertEquals("jotain", exercise.getExerciseType().getName());
    }


    //workout tests

    @Test
    public void testGetWname(){
        workout.setName("workout");
        assertEquals("workout", workout.getName());
    }

    @Test
    public void testGetWStartedAndEnded(){
        Date date = new Date(System.currentTimeMillis());
        workout.setWorkoutStarted(date);
        workout.setWorkoutEnded(date);
        assertEquals(date, workout.getWorkoutStarted());
        assertEquals(date, workout.getWorkoutEnded());

    }

    @Test
    public void testGetWid(){
        workout.setId("44");
        assertEquals("44", workout.getId());
    }

    @Test
    public void testIsPreset(){
        assertFalse(workout.preset());
        workout.setPreset(true);
        assertTrue(workout.preset());
    }


    @Test
    public void testExList(){
        ArrayList<Exercise> e = new ArrayList<>();
        e.add(exercise);
        workout.setExercises(e);
        assertEquals("ex", workout.getExercises().get(0).getExerciseName());
        Exercise exercise1 = new Exercise();
        exercise1.setExerciseType(new ExerciseType("ex2"));
        workout.addExerciseToList(exercise1);
        assertEquals("ex2", workout.getExercises().get(1).getExerciseName());

    }

    //User tests

    @Test
    public void testGetUsername(){
        user.setUsername("testiUser");
        assertEquals("testiUser", user.getUsername());
    }


}

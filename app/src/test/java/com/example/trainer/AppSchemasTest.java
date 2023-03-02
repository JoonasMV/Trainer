package com.example.trainer;

import static org.junit.Assert.assertEquals;

import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.database.schemas.User;
import com.example.trainer.database.schemas.Workout;

import org.junit.Before;
import org.junit.BeforeClass;
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
        exercise = new Exercise("ex", 1, 2, 3, new ArrayList<>());
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
        type.setName("tyyppi2");
        assertEquals("tyyppi2", type.getName());
        type.setId(1);
        assertEquals(1, type.getId());


    }


    //exercise set tests

    @Test
    public void testGetWeight(){
        set.setWeight(40.4);
        assertEquals(40.4, set.getWeight(), 0);
    }

    @Test
    public void testGetAmount(){
        set.setAmount(10);
        assertEquals(10, set.getAmount(), 0);
    }
    @Test
    public void testGetId(){
        set.setId(3);
        assertEquals(3, set.getId(), 0);
    }


    @Test
    public void testToString(){
        set = new ExerciseSet();
        set.setId(1);
        set.setAmount(5);
        set.setWeight(4);
        assertEquals("ExerciseSet{, weight=4.0, amount=5, id=1}", set.toString());
    }


    //Exercise tests

    @Test
    public void testGetName(){
        exercise.setExerciseName("name");
        assertEquals("name", exercise.getExerciseName());
    }
    @Test
    public void testGetWorkoutId(){
        exercise.setWorkoutId(3);
        assertEquals(3, exercise.getWorkoutId());
    }
    @Test
    public void testGetExId(){
        exercise.setExerciseId(55);
        assertEquals(55, exercise.getExerciseId());
    }
    @Test
    public void testGetSetList(){
        ExerciseSet set1 = new ExerciseSet();
        set1.setWeight(44);
        set1.setAmount(10);
        exercise.setSetList(new ArrayList<>());
        exercise.addSet(set1);
        assertEquals(44, exercise.getSetList().get(0).getWeight(), 0);
        assertEquals(10, exercise.getSetList().get(0).getAmount(), 0);
    }
    @Test
    public void testGetTypeId(){
        exercise.setTypeId(33);
        assertEquals(33, exercise.getTypeId());
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
        workout.setId(44);
        assertEquals(44, workout.getId());
    }

    @Test
    public void testIsPreset(){
        assertEquals(false, workout.isPreset());
        workout.setPreset(true);
        assertEquals(true, workout.isPreset());
    }


    @Test
    public void testExList(){
        ArrayList<Exercise> e = new ArrayList<>();
        e.add(exercise);
        workout.setExList(e);
        assertEquals("ex", workout.getExList().get(0).getExerciseName());
        workout.addExerciseToList(new Exercise("ex2", 2, 3, 4, new ArrayList<>()));
        assertEquals("ex2", workout.getExList().get(1).getExerciseName());

    }

    @Test
    public void testGetWUserId(){
        workout.setUserId(55);
        assertEquals(55, workout.getUserId());
    }


    //User tests

    @Test
    public void testGetUsername(){
        user.setUsername("testiUser");
        assertEquals("testiUser", user.getUsername());
    }


}

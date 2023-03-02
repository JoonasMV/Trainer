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





}

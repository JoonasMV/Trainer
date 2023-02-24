package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.util.WorkoutSerializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import com.example.trainer.R;

@RunWith(JUnit4.class)
public class WorkoutSerializerTests {

    private static Context context;


    @Before
    public void setup(){
        context = InstrumentationRegistry.getInstrumentation().getContext();
        WorkoutSerializer.clearPrefs(context);
    }

    @Test
    public void writeToPref_and_readFromPref(){
        Workout workout = new Workout("mock", new Date(), new Date());

        workout.addExerciseToList(new Exercise("mockName", 1, 1, 1, null));

        WorkoutSerializer.writeWorkoutToPref(workout, context);

        Workout workoutFromPref = WorkoutSerializer.readWorkoutFromPref(context);

        assertNotNull(workoutFromPref);

        assertEquals("mock", workoutFromPref.getName());

        assertEquals(1, workout.getExList().size());


    }






}

package com.example.trainer;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.SetDAO;
import com.example.trainer.schemas.ExerciseSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SetDAOTests {

    private static Context ctx;

    private static SetDAO setDAO;


    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getContext();
        DatabaseHelper.initialize(ctx);

        setDAO = new SetDAO();
    }

    @Before
    public void beforeEach(){
        setDAO.deleteAllSets();
    }

    @Test
    public void set_creation_and_findById(){
        ExerciseSet set = new ExerciseSet(20, 20);
        List<ExerciseSet> sets = new ArrayList<>();

        sets.add(set);
        setDAO.saveMany(sets, 10);

        List<ExerciseSet> setsFromDb = setDAO.getSetsByExerciseId(10);

        assertEquals(1, setsFromDb.size());

        assertEquals(20, setsFromDb.get(0).getAmount(), 0);
    }

}

package com.example.trainer.workouts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //LinearLayout workoutParent = findViewById(R.id.workoutParent);


        ArrayList<String> workouts = new ArrayList<>();
        Workout workout = new Workout("test1", new Date(), new Date());

//        RecyclerView workoutList = findViewById(R.id.workoutList);
//        WorkoutListAdapter adapter = new WorkoutListAdapter(this, workouts);
//        workoutList.setAdapter(adapter);
//        workoutList.setLayoutManager(new LinearLayoutManager(this));



    }
}
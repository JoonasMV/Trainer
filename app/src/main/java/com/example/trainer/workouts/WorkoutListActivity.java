package com.example.trainer.workouts;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.workouts.WorkoutListAdapter;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_selection);

        LinearLayout workoutParent = findViewById(R.id.workoutParent);


        ArrayList<String> workouts = new ArrayList<>();
        Workout workout = new Workout("test1", new Date(), new Date());

        RecyclerView workoutList = findViewById(R.id.workoutList);
        WorkoutListAdapter adapter = new WorkoutListAdapter(this, workouts);
        workoutList.setAdapter(adapter);
        workoutList.setLayoutManager(new LinearLayoutManager(this));



    }
}
package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CurrentWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_workout);

        RecyclerView listOfWorkouts = findViewById(R.id.listOfWorkouts);

        listOfWorkouts.setAdapter(new listOfWorkoutsAdapter());
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(this));

    }
}
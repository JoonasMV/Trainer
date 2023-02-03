package com.example.trainer.workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.trainer.R;
import com.example.trainer.workouts.exercises.SelectExerciseActivity;

public class CurrentWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_workout);

        RecyclerView listOfWorkouts = findViewById(R.id.listOfWorkouts);

        listOfWorkouts.setAdapter(new listOfWorkoutsAdapter());
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(this));

        Button test = findViewById(R.id.button2);
        test.setOnClickListener(v -> {
            Intent i = new Intent(this, SelectExerciseActivity.class);
            startActivity(i);
        });
    }
}
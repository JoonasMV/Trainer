package com.example.trainer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_selection);
        ArrayList<String> workouts = new ArrayList<String>();

        ListView workoutList = findViewById(R.id.workoutList);
        workoutList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));

    }
}
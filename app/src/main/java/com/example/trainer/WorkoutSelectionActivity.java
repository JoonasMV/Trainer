package com.example.trainer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_selection);

        ArrayList<String> workouts = new ArrayList<>();
        workouts.add("test1");
        workouts.add("test2");
        workouts.add("test3");

        RecyclerView workoutList = findViewById(R.id.workoutList);
        WorkoutListAdapter adapter = new WorkoutListAdapter(this, workouts);
        workoutList.setAdapter(adapter);
        workoutList.setLayoutManager(new LinearLayoutManager(this));



    }
}
package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.workouts.currentWorkout.SelectExercise;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;


public class AddWorkoutName extends Fragment {

    private final WorkoutManager workoutManager = WorkoutManager.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_workout_name, container, false);

        v.findViewById(R.id.button).setOnClickListener(view ->{
            TextView tv = v.findViewById(R.id.workout);
            String name = tv.getText().toString();
            workoutManager.startWorkout(name);
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new SelectExercise()).commit();
        });

        return v;
    }
}
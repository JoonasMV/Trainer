package com.example.trainer.workouts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.workouts.currentWorkout.SelectExercise;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;


public class addWorkoutName extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private final WorkoutManager workoutManager = WorkoutManager.getInstance();

    private String name;


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
            name = tv.getText().toString();
            workoutManager.setName(name);
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new SelectExercise()).commit();
        });

        return v;
    }
}
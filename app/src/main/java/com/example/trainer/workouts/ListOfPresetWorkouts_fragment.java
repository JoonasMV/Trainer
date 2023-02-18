package com.example.trainer.workouts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.workouts.currentWorkout.SelectExercise;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;


public class ListOfPresetWorkouts_fragment extends Fragment {

    private WorkoutManager workoutManager;

    public ListOfPresetWorkouts_fragment() {
        // Required empty public constructor
    }


    public static ListOfPresetWorkouts_fragment newInstance(String param1, String param2) {
        ListOfPresetWorkouts_fragment fragment = new ListOfPresetWorkouts_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutManager = WorkoutManager.getInstance();

        if (workoutManager.workoutActive()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkoutFragment())
                    .commit();
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_preset_workouts, container, false);

        view.findViewById(R.id.newWorkoutBtn).setOnClickListener(v -> { startNewWorkout(); });

        return view;
    }

    private void startNewWorkout() {
        if (!workoutManager.workoutActive()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new addWorkoutName())
                    .commit();
        } else {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkoutFragment())
                    .commit();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }
}

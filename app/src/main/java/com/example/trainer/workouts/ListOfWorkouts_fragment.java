package com.example.trainer.workouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trainer.MainActivity;
import com.example.trainer.R;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;
import com.example.trainer.workouts.currentWorkout.WorkoutViewModel;


public class ListOfWorkouts_fragment extends Fragment {

    private Button newWorkoutBtn;
    private WorkoutManager workoutManager;

    public ListOfWorkouts_fragment() {
        // Required empty public constructor
    }


    public static ListOfWorkouts_fragment newInstance(String param1, String param2) {
        ListOfWorkouts_fragment fragment = new ListOfWorkouts_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutManager = WorkoutManager.getInstance();

        Fragment fragment = getParentFragmentManager().findFragmentByTag("CurrentWorkout");
        System.out.println(fragment);
        if (workoutManager.workoutActive()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_list_of_workouts_fragment, container, false);

        view.findViewById(R.id.newWorkoutBtn).setOnClickListener(v -> { startNewWorkout(); });

        return view;
    }

    private void startNewWorkout() {
        workoutManager.initWorkout();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, new CurrentWorkoutFragment(), "CurrentWorkout")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }
}

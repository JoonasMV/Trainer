package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.WelcomeScreen_fragment;
import com.example.trainer.workouts.ListOfWorkouts_fragment;
import com.example.trainer.workouts.currentWorkout.adapters.ExerciseAdapter;

public class CurrentWorkoutFragment extends Fragment {
    private ExerciseAdapter exerciseAdapter;
    private final WorkoutManager workoutManager = WorkoutManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        getParentFragmentManager().beginTransaction();

        v.findViewById(R.id.cancelWorkoutBtn).setOnClickListener(view -> {
            workoutManager.cancelWorkout();
            fragmentHandler(new ListOfWorkouts_fragment());
        });

        v.findViewById(R.id.addExerciseBtn).setOnClickListener(view -> {
            fragmentHandler(new SelectExercise());
        });

        v.findViewById(R.id.endWorkoutBtn).setOnClickListener(view -> {
            workoutManager.saveWorkout();
            fragmentHandler(new WelcomeScreen_fragment());
        });

        TextView workoutName = v.findViewById(R.id.workoutName);


        workoutName.setText(workoutManager.getWorkout().getName());

        initRecyclerView(v);
        return v;
    }

    private void initRecyclerView(View v) {
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        exerciseAdapter = new ExerciseAdapter(getContext());
        listOfWorkouts.setAdapter(exerciseAdapter);
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void fragmentHandler(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment.getClass(), null)
                .addToBackStack(null)
                .commit();
    }
}
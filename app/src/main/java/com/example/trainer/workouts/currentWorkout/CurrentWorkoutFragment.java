package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.ListOfWorkouts_fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CurrentWorkoutFragment extends Fragment {

    private Workout currentWorkout;
    private ExerciseAdapter exerciseAdapter;
    private WorkoutViewModel workoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        v.findViewById(R.id.cancelWorkoutBtn).setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, ListOfWorkouts_fragment.class, null)
                    .commit();
        });

        v.findViewById(R.id.addExerciseBtn).setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, new SelectExercise(), null)
                    .commit();
        });
        TextView workoutName = v.findViewById(R.id.workoutName);

        workoutManager = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        currentWorkout = workoutManager.getWorkout();
        workoutName.setText(currentWorkout.getName());

        // Recycler view initiation
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        exerciseAdapter = new ExerciseAdapter(currentWorkout, getContext());
        listOfWorkouts.setAdapter(exerciseAdapter);
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
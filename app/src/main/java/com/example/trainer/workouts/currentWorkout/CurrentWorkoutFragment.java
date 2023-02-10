package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.WelcomeScreen_fragment;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.ListOfWorkouts_fragment;

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

        workoutManager = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        v.findViewById(R.id.cancelWorkoutBtn).setOnClickListener(view -> {
            workoutManager.cancelWorkout();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, ListOfWorkouts_fragment.class, null)
                    .commit();
        });

        v.findViewById(R.id.addExerciseBtn).setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, new SelectExercise(), null)
                    .commit();
        });

        v.findViewById(R.id.endWorkoutBtn).setOnClickListener(view -> {
            workoutManager.saveWorkout(exerciseAdapter.getWorkout());
            workoutManager.cancelWorkout();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, new WelcomeScreen_fragment(), null)
                    .commit();
        });

        TextView workoutName = v.findViewById(R.id.workoutName);


        currentWorkout = workoutManager.getWorkout();
        workoutName.setText(currentWorkout.getName());

        initRecyclerView(v);
        return v;
    }

    private void initRecyclerView(View v) {
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        exerciseAdapter = new ExerciseAdapter(currentWorkout, getContext());
        listOfWorkouts.setAdapter(exerciseAdapter);
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
package com.example.trainer.workouts.workoutHistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.schemas.Workout;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import java.util.ArrayList;

public class WorkoutHistory_fragment extends Fragment {


    public WorkoutHistory_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_workout_history, container, false);
        return v;
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState) {

        RecyclerView workoutHistory = view.findViewById(R.id.workoutHistoryRV);

        ArrayList<Workout> list = new ArrayList<>(WorkoutManager.getInstance().getNonPresetWorkouts());

        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter(list);
        workoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutHistory.setAdapter(adapter);
    }
}
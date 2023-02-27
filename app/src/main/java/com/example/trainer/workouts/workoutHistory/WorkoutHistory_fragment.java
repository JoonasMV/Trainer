package com.example.trainer.workouts.workoutHistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutHistory_fragment extends Fragment {

    WorkoutDAO workoutDAO;

    public WorkoutHistory_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutDAO = new WorkoutDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout_history, container, false);
        return v;
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState) {

        RecyclerView workoutHistory = view.findViewById(R.id.workoutHistoryRV);

        List<Workout> workouts = workoutDAO.getAll();

        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter(workouts);
        workoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutHistory.setAdapter(adapter);
    }
}
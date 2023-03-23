package com.example.trainer.user_interface.workouts.workoutHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.schemas.Workout;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        return inflater.inflate(R.layout.fragment_workout_history, container, false);
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState) {

        RecyclerView workoutHistory = view.findViewById(R.id.workoutHistoryRV);

        ArrayList<Workout> list = new ArrayList<>(BaseController.getController().getNonPresetWorkouts());

        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter(list);
        workoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutHistory.setAdapter(adapter);
    }
}
package com.example.trainer.UI.workouts.workoutHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.UpdatableAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.List;

public class WorkoutHistory_fragment extends Fragment {


    private ProgressBar progressBar;
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

        return inflater.inflate(R.layout.workout_history_fragment, container, false);
    }

    @Override
    public void onViewCreated (View view,
                               Bundle savedInstanceState) {

        progressBar = view.findViewById(R.id.historyProgresBar);
        RecyclerView workoutHistory = view.findViewById(R.id.workoutHistoryRV);

        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter();

        progressBar.setProgress(0);
        BaseController.getController().getNonPresetWorkouts(result -> {
            getActivity().runOnUiThread(() -> {
                adapter.update(result);
                progressBar.setVisibility(View.GONE);
            });
        });
        workoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutHistory.setAdapter(adapter);
    }
}
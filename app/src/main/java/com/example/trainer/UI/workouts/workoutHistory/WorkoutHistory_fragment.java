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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

        RecyclerView workoutHistory = view.findViewById(R.id.workoutHistoryRV);
        progressBar = view.findViewById(R.id.historyProgresBar);

        WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter();
        handleWorkoutFetching(adapter);
        workoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutHistory.setAdapter(adapter);
    }

    private void handleWorkoutFetching(WorkoutHistoryAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            Future<List<Workout>> result = BaseController.getController().getNonPresetWorkoutsAsync();
            try {
                List<Workout> workouts = result.get();
                getActivity().runOnUiThread(() -> {
                    adapter.update(workouts);
                    progressBar.setVisibility(View.GONE);
                });
            } catch (InterruptedException | ExecutionException e) {
                Toaster.toast(getContext(), "Failed to load workouts");
            }
        }).start();

    }
}
package com.example.trainer.ui.workouts.workoutHistory;

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
import com.example.trainer.ui.UpdatableAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.Workout;

import java.util.List;

/**
 * Fragment for displaying the workout history of the user
 */
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

    /**
     * Fetches the workouts from the database and updates the adapter
     * @param adapter the adapter to update
     */
    private void handleWorkoutFetching(WorkoutHistoryAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            List<Workout> workouts = BaseController.getController().getNonPresetWorkouts();
            getActivity().runOnUiThread(UIRunnable(adapter, workouts));
        }).start();

    }

    /**
     * Creates a runnable that updates the adapter and hides the progress bar
     * @param adapter the adapter to update
     * @param workouts the workouts to update the adapter with
     * @return the runnable
     */
    private Runnable UIRunnable(WorkoutHistoryAdapter adapter, List<Workout> workouts){
        return () -> {
            adapter.update(workouts);
            progressBar.setVisibility(View.GONE);
        };
    }
}
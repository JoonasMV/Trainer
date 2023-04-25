package com.example.trainer.UI.workouts.presetWorkouts;

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
import com.example.trainer.UI.workouts.workoutHistory.WorkoutHistoryAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Workout;
import com.example.trainer.UI.workouts.currentWorkout.CurrentWorkout_fragment;
import com.example.trainer.UI.workouts.currentWorkout.SelectExercise_fragment;
import com.example.trainer.util.Toaster;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class PresetWorkouts_fragment extends Fragment {

    private TrainerController workoutManager;

    private ProgressBar progressBar;


    public PresetWorkouts_fragment() {
        // Required empty public constructor
    }


    public static PresetWorkouts_fragment newInstance(String param1, String param2) {

        return new PresetWorkouts_fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutManager = BaseController.getController();

        if (workoutManager.workoutActive()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkout_fragment())
                    .commit();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.preset_workouts_fragment, container, false);

        view.findViewById(R.id.newWorkoutBtn).setOnClickListener(v -> startNewWorkout());

        return view;
    }

    private void startNewWorkout() {
        if (!workoutManager.workoutActive()) {
            workoutManager.startWorkout("New Workout");
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new SelectExercise_fragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkout_fragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        RecyclerView presets = view.findViewById(R.id.workoutList);
        PresetWorkoutsAdapter adapter = new PresetWorkoutsAdapter(getParentFragmentManager());

        progressBar = view.findViewById(R.id.presetsProgressBar);
        handleWorkoutFetching(adapter);
        presets.setLayoutManager(new LinearLayoutManager(getContext()));
        presets.setAdapter(adapter);

    }

    private void handleWorkoutFetching(PresetWorkoutsAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            Future<List<Workout>> result = BaseController.getController().getPresetWorkoutsAsync();
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

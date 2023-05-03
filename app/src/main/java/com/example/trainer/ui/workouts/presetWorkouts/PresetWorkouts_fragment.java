package com.example.trainer.ui.workouts.presetWorkouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.diagram.api.controllers.BaseController;
import com.example.trainer.diagram.api.controllers.TrainerController;
import com.example.trainer.diagram.api.model.Workout;
import com.example.trainer.ui.workouts.currentWorkout.CurrentWorkout_fragment;
import com.example.trainer.ui.workouts.currentWorkout.SelectExercise_fragment;

import java.util.List;


/**
 * Fragment for displaying the preset workouts of the user
 */
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
            workoutManager.startWorkout(getString(R.string.newWorkout));
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

    /**
     * Fetches the workouts on the background and updates the adapter
     * @param adapter the adapter to update
     */
    private void handleWorkoutFetching(PresetWorkoutsAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            List<Workout> workouts = workoutManager.getPresetWorkouts();
            FragmentActivity activity = getActivity();
            if (activity == null) return;
            activity.runOnUiThread(UIRunnable(adapter, workouts));
        }).start();
    }

    private Runnable UIRunnable(PresetWorkoutsAdapter adapter, List<Workout> workouts) {
        return () -> {
            adapter.update(workouts);
            progressBar.setVisibility(View.GONE);
        };
    }
}

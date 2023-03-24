package com.example.trainer.UI.workouts;

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
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.schemas.Workout;
import com.example.trainer.UI.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.UI.workouts.currentWorkout.SelectExercise;

import java.util.List;


public class ListOfPresetWorkouts_fragment extends Fragment {

    private TrainerController workoutManager;


    public ListOfPresetWorkouts_fragment() {
        // Required empty public constructor
    }


    public static ListOfPresetWorkouts_fragment newInstance(String param1, String param2) {

        return new ListOfPresetWorkouts_fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutManager = BaseController.getController();

        if (workoutManager.workoutActive()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkoutFragment())
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
            workoutManager.startWorkout("asd");
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new SelectExercise())
                    .addToBackStack(null)
                    .commit();
        } else {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new CurrentWorkoutFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        RecyclerView presets = view.findViewById(R.id.workoutList);

        List<Workout> workouts = workoutManager.getPresetWorkouts();

        PresetAdapter adapter = new PresetAdapter(workouts, getParentFragmentManager());
        presets.setLayoutManager(new LinearLayoutManager(getContext()));
        presets.setAdapter(adapter);

    }
}

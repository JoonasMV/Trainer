package com.example.trainer.UI.exercises.exerciseList;

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
import com.example.trainer.UI.exercises.CreateExercise_fragment;
import com.example.trainer.UI.workouts.presetWorkouts.PresetWorkoutsAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExerciseList_fragment extends Fragment {
    RecyclerView exerciseList;

    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        if (container != null) {
//            container.removeAllViews();
//        }
        return inflater.inflate(R.layout.exercise_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        exerciseList = requireView().findViewById(R.id.listOfExercises);
        ExerciseListAdapter adapter = new ExerciseListAdapter();
        progressBar = view.findViewById(R.id.exercisesProggressBar);
        handleExerciseTypeFetching(adapter);

        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        requireView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());
    }

    private void handleExerciseTypeFetching(ExerciseListAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            Future<List<ExerciseType>> result = BaseController.getController().getExerciseTypesAsync();
            try {
                List<ExerciseType> types = result.get();
                getActivity().runOnUiThread(() -> {
                    adapter.update(types);
                    progressBar.setVisibility(View.GONE);
                });
            } catch (InterruptedException | ExecutionException e) {
                Toaster.toast(getContext(), "Failed to load workouts");
            }
        }).start();
    }
    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, CreateExercise_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}
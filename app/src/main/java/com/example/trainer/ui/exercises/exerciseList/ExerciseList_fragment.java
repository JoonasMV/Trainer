package com.example.trainer.ui.exercises.exerciseList;

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
import com.example.trainer.ui.exercises.CreateExercise_fragment;
import com.example.trainer.diagram.api.controllers.BaseController;
import com.example.trainer.diagram.api.model.ExerciseType;

import java.util.List;

/**
 * Represents a list of exercises the user has created, in the model these exercises are referred with the term ExerciseType
 */
public class ExerciseList_fragment extends Fragment {
    RecyclerView exerciseList;

    /**
     * Progress bar to show the user that the exercises are being fetched from the database
     */
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

    /**
     * Fetches the exercises from the database and updates the adapter.
     * This method is non blocking.
     * @param adapter the adapter to update
     */
    private void handleExerciseTypeFetching(ExerciseListAdapter adapter){
        progressBar.setProgress(0);
        new Thread(() -> {
            List<ExerciseType> types = BaseController.getController().getExerciseTypes();
            FragmentActivity activity = getActivity();
            if (activity == null) return;
            activity.runOnUiThread(UIRunnable(adapter, types));
        }).start();
    }

    /**
     * Creates a runnable that updates the adapter and hides the progress bar
     * @param adapter the adapter to update
     * @param types the list of exercises to update the adapter with
     * @return the runnable
     */
    private Runnable UIRunnable(ExerciseListAdapter adapter, List<ExerciseType> types){
        return () -> {
            adapter.update(types);
            progressBar.setVisibility(View.GONE);
        };
    }

    /**
     * Navigates to the fragment where the user can create a new exercise
     */
    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, CreateExercise_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}
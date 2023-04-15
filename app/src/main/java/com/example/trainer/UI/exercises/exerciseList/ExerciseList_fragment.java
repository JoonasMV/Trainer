package com.example.trainer.UI.exercises.exerciseList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.exercises.CreateExercise_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.ExerciseType;

import java.util.List;
import java.util.Objects;

public class ExerciseList_fragment extends Fragment {
    RecyclerView exerciseList;
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
        return inflater.inflate(R.layout.exercise_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        List<ExerciseType> typesFromDb = BaseController.getController().getExerciseTypes();

        exerciseList = requireView().findViewById(R.id.listOfExercises);
        exerciseList.setAdapter(new ExerciseListAdapter(typesFromDb));
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        requireView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, CreateExercise_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}
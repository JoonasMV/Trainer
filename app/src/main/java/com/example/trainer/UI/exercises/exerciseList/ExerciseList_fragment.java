package com.example.trainer.UI.exercises.exerciseList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.exercises.CreateExercise_fragment;

public class ExerciseList_fragment extends Fragment {
    RecyclerView exerciseList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.exercise_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        exerciseList = getView().findViewById(R.id.listOfExercises);
        ExerciseListAdapter adapter = new ExerciseListAdapter();
        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        getView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, CreateExercise_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}
package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;

public class ListOfExercises_fragment extends Fragment {
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
        return inflater.inflate(R.layout.list_of_exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        exerciseList = getView().findViewById(R.id.listOfExercises);
        ListOfExercisesAdapter adapter = new ListOfExercisesAdapter();
        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        getView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, NewExercise.class, null)
                .addToBackStack(null)
                .commit();
    }
}
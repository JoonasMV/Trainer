package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ListOfExercises_fragment extends Fragment {
    ExerciseDAO exerciseDAO;
    RecyclerView exerciseList;
    ArrayList<Exercise> listOfExercises = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        exerciseDAO = new ExerciseDAO(this.getContext());
        listOfExercises = exerciseDAO.getAllExercises();

        exerciseList = getView().findViewById(R.id.listOfExercises);
        ListOfExercisesAdapter adapter = new ListOfExercisesAdapter(listOfExercises, exerciseDAO);
        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        getView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());

        // Animation testing
        getView().findViewById(R.id.tempTestBtn).setOnClickListener(v -> {
            for (int i = 0; i < 3; i++) {
                Exercise ex = new Exercise(new String("test "+i));
                listOfExercises.add(ex);
                exerciseDAO.addExercise(ex);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, new_exercise.class, null)
                .addToBackStack(null)
                .commit();
    }
}
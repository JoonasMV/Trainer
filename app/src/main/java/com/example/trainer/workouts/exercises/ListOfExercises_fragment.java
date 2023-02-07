package com.example.trainer.workouts.exercises;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.new_exercise;

import java.util.ArrayList;

public class ListOfExercises_fragment extends Fragment {
    ExerciseDAO exerciseDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        exerciseDAO = new ExerciseDAO(this.getContext());
        handleExercisesToDisplay();

        getView().findViewById(R.id.addExercise)
                .setOnClickListener(v -> { goToNewExerciseFragment(); });
    }

    private void handleExercisesToDisplay() {
        ArrayList<Exercise> listOfExercises = exerciseDAO.getAllExercises();
        if (listOfExercises.size() <= 0) return;

        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (Exercise exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getExerciseName());
        }

        ListView lv = getView().findViewById(R.id.exerciseList);
        lv.setAdapter(new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView2, new_exercise.class, null)
                .addToBackStack(null)
                .commit();
    }
}
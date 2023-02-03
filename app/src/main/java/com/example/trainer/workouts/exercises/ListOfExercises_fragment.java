package com.example.trainer.workouts.exercises;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        exerciseDAO = new ExerciseDAO(this.getContext());



        handleExercisesToDisplay();
        ListView lv = getView().findViewById(R.id.exerciseList);

        lv.setOnItemClickListener((adapterView, v, i, l) -> {
            //siirretty select exercise activityyn
        });
        getView().findViewById(R.id.tempTestBtn).setOnClickListener(v -> {
        });



        Button addExercise = getView().findViewById(R.id.addExercise);
        addExercise.setOnClickListener(v -> {
            startActivity(new Intent(this.getContext(), NewExerciseActivity.class));
        });
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
}
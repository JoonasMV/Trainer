package com.example.trainer.workouts.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;

import java.util.ArrayList;


public class selectExercise extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public selectExercise() {
        // Required empty public constructor
    }

    ExerciseDAO exerciseDAO;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_select_exercise, container, false);

        exerciseDAO = new ExerciseDAO(getContext());

        lv = v.findViewById(R.id.lista);
        handleExercisesToDisplay();



        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag", "onclick");
            ArrayList<Exercise> exercises = exerciseDAO.getAllExercises();
            Exercise exercise = exercises.get(i);
            String exerciseName = exercise.getExerciseName();
            System.out.println(exerciseName);
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, CurrentWorkoutFragment.class, null).commit();
        });

        return v;
    }


    private void handleExercisesToDisplay() {
        ArrayList<Exercise> listOfExercises = exerciseDAO.getAllExercises();
        if (listOfExercises.size() <= 0) return;

        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (Exercise exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getExerciseName());
        }

//        ListView lv = getView().findViewById(R.id.lista);
        lv.setAdapter(new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));
    }
}
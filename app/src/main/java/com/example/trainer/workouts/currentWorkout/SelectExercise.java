package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;


public class SelectExercise extends Fragment {

    private ExerciseDAO exerciseDAO;
    private ListView lv;
    private WorkoutManager workoutManager = WorkoutManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_select_exercise, container, false);

        exerciseDAO = new ExerciseDAO();

        lv = v.findViewById(R.id.lista);
        handleExercisesToDisplay();

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag", "onclick");
            ArrayList<Exercise> exercises = exerciseDAO.getAllExercises();
            Exercise newExercise = exercises.get(i);

            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new CurrentWorkoutFragment()).commit();
            workoutManager.addExercise(new Exercise(newExercise.getExerciseName()));
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
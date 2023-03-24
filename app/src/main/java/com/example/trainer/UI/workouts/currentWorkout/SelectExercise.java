package com.example.trainer.UI.workouts.currentWorkout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.database.legacyDAO.ExerciseDAO;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseType;

import java.util.ArrayList;


public class SelectExercise extends Fragment {

    private ExerciseDAO exerciseDAO;
    private ListView lv;
    private final TrainerController workoutManager = BaseController.getController();

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_select_exercise, container, false);

        exerciseDAO = new ExerciseDAO();

        lv = v.findViewById(R.id.lista);
        handleExercisesToDisplay();

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag", "onclick");
            ArrayList<ExerciseType> exercises = new ArrayList<>(exerciseDAO.getAllExerciseTypes());
            if(!exercises.isEmpty()){
                ExerciseType newExercise = exercises.get(i);

                getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new CurrentWorkoutFragment()).commit();
                //TODO: add workout id to exercise when saving workout
                workoutManager.addExercise(new Exercise(newExercise.getId()));
            }

        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void handleExercisesToDisplay() {
        ArrayList<ExerciseType> listOfExercises = new ArrayList<>(exerciseDAO.getAllExerciseTypes());
        if (listOfExercises.size() <= 0) return;

        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (ExerciseType exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getName());
        }

//        ListView lv = getView().findViewById(R.id.lista);
        lv.setAdapter(new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));
    }
}
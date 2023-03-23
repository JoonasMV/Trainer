package com.example.trainer.user_interface.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.util.Toaster;

public class NewExercise extends Fragment {

    private TrainerController workoutManager;
    public NewExercise() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_exercise, container, false);

        workoutManager = BaseController.getController();
        TextView exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        v.findViewById(R.id.newExerciseBtn).setOnClickListener(view -> {
            String name = exerciseNameInput.getText().toString();
            if (workoutManager.exerciseTypeExists(name)) {
                workoutManager.createExerciseType(new ExerciseType(name));
                getParentFragmentManager().popBackStack();
            } else{
                Toaster.toast(getContext(),"Exercise already added");
            }
        });
        return v;
    }
}
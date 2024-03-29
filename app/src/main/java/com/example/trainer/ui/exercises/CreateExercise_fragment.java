package com.example.trainer.ui.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.util.Toaster;

/**
 * Fragment for creating a new exercise, in the model these exercises are referred with the term ExerciseType
 */
public class CreateExercise_fragment extends Fragment {

    private TrainerController workoutManager;
    public CreateExercise_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.create_exercise_fragment, container, false);

        workoutManager = BaseController.getController();
        TextView exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        v.findViewById(R.id.newExerciseBtn).setOnClickListener(view -> {
            String name = exerciseNameInput.getText().toString();
            if (!workoutManager.exerciseTypeExists(name)) {
                workoutManager.createExerciseType(new ExerciseType(name));
                getParentFragmentManager().popBackStack();
            } else if (name.isEmpty()){
                Toaster.toast(getContext(), getString(R.string.nameNotEmpty));
            }else {
                Toaster.toast(getContext(),  getString(R.string.exerciseToast));
            }
        });
        return v;
    }
}
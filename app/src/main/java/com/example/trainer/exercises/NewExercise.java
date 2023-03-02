package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.util.Toaster;

public class NewExercise extends Fragment {

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
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        View v = inflater.inflate(R.layout.fragment_new_exercise, container, false);

        TextView exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        v.findViewById(R.id.newExerciseBtn).setOnClickListener(view -> {
            String name = exerciseNameInput.getText().toString();
            if (exerciseDAO.getExerciseTypeByName(name.toLowerCase()) == null) {
                exerciseDAO.addExerciseType(new ExerciseType(name));
                getParentFragmentManager().popBackStack();
            } else{
                Toaster.toast(getContext(),"Exercise already added");
            }
        });
        return v;
    }
}
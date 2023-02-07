package com.example.trainer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

public class new_exercise extends Fragment {

    public new_exercise() {
        // Required empty public constructor
    }

    public static new_exercise newInstance(String param1, String param2) {
        new_exercise fragment = new new_exercise();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ExerciseDAO exerciseDAO = new ExerciseDAO(getContext());
        View v = inflater.inflate(R.layout.fragment_new_exercise, container, false);

        TextView exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        v.findViewById(R.id.newExerciseBtn).setOnClickListener(view -> {
            System.out.println(exerciseDAO);
            exerciseDAO.addExercise(new Exercise(exerciseNameInput.getText().toString()));
            getParentFragmentManager().popBackStack();
        });

        return v;
    }
}
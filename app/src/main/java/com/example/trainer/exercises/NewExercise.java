package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.ExerciseType;

public class NewExercise extends Fragment {

    public NewExercise() {
        // Required empty public constructor
    }

    public static NewExercise newInstance(String param1, String param2) {
        NewExercise fragment = new NewExercise();
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
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        View v = inflater.inflate(R.layout.fragment_new_exercise, container, false);

        TextView exerciseNameInput = v.findViewById(R.id.exerciseNameInput);
        v.findViewById(R.id.newExerciseBtn).setOnClickListener(view -> {
            System.out.println(exerciseDAO);
            String name = exerciseNameInput.getText().toString();
            if (exerciseDAO.getExerciseTypeByName(name.toLowerCase()) == null) {
                exerciseDAO.addExerciseType(new ExerciseType(name));
                getParentFragmentManager().popBackStack();
            } else{
                Toast.makeText(getActivity(),"This exercise is already in the database",Toast.LENGTH_SHORT).show();
            }


        });

        return v;
    }
}
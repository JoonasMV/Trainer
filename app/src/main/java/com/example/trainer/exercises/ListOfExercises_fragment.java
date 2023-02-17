package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.ExerciseType;

import java.util.ArrayList;
import java.util.List;

public class ListOfExercises_fragment extends Fragment {
    ExerciseDAO exerciseDAO;
    RecyclerView exerciseList;
    List<ExerciseType> listOfExercises;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseDAO = new ExerciseDAO();
        listOfExercises = new ArrayList<>(exerciseDAO.getAllExerciseTypes());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_exercises_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        exerciseDAO = new ExerciseDAO();
        listOfExercises = exerciseDAO.getAllExerciseTypes();

        exerciseList = getView().findViewById(R.id.listOfExercises);
        ListOfExercisesAdapter adapter = new ListOfExercisesAdapter(listOfExercises);
        exerciseList.setAdapter(adapter);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        getView().findViewById(R.id.addExercise).setOnClickListener(v -> goToNewExerciseFragment());
    }

    private void goToNewExerciseFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, NewExercise.class, null)
                .addToBackStack(null)
                .commit();
    }
}
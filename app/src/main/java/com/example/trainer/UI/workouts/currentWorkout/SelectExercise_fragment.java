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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.workouts.currentWorkout.adapters.SelectExerciseAdapter;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStatsExerciseAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;

import java.util.List;
import java.util.stream.Collectors;


public class SelectExercise_fragment extends Fragment {

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

        return inflater.inflate(R.layout.fragment_select_exercise, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView exerciseTypes = view.findViewById(R.id.typeList);

        List<ExerciseType> typesFromDb = workoutManager.getExerciseTypes();

        SelectExerciseAdapter adapter = new SelectExerciseAdapter(typesFromDb, getContext(), workoutManager);
        exerciseTypes.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseTypes.setAdapter(adapter);

    }


}
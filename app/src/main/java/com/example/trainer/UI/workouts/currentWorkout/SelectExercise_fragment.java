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
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;

import java.util.List;
import java.util.stream.Collectors;


public class SelectExercise_fragment extends Fragment {

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


        lv = v.findViewById(R.id.lista);
        handleDisplayExerciseTypes();

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag", "onclick");
            List<ExerciseType> exerciseTypes = workoutManager.getExerciseTypes();
            if(!exerciseTypes.isEmpty()){
                ExerciseType type = exerciseTypes.get(i);
                Exercise newExercise = new Exercise();
                newExercise.setExerciseType(type);
                getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new CurrentWorkout_fragment()).commit();
                //TODO: add workout id to exercise when saving workout
                workoutManager.addExercise(newExercise);
            }

        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void handleDisplayExerciseTypes() {
        List<ExerciseType> exerciseTypes = workoutManager.getExerciseTypes();
        List<String> exerciseTypesAsStrings = exerciseTypes
                .stream()
                .map(ExerciseType::getName)
                .collect(Collectors.toList());
        lv.setAdapter(new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                exerciseTypesAsStrings
        ));
    }
}
package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.WelcomeScreen_fragment;
import com.example.trainer.workouts.ListOfPresetWorkouts_fragment;
import com.example.trainer.workouts.currentWorkout.adapters.ExerciseAdapter;

public class CurrentWorkoutFragment extends Fragment {
    private ExerciseAdapter exerciseAdapter;
    private final WorkoutManager workoutManager = WorkoutManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);
        if(!workoutManager.workoutActive()) {
            changeFragment(new ListOfPresetWorkouts_fragment());
            return v;
        }

        TextView workoutName = v.findViewById(R.id.workoutName);

            v.findViewById(R.id.cancelWorkoutBtn).setOnClickListener(view -> {
            workoutManager.cancelWorkout(getContext());
            changeFragment(new ListOfPresetWorkouts_fragment());
        });

        v.findViewById(R.id.addExerciseBtn).setOnClickListener(view -> {
            changeFragment(new SelectExercise());
        });

        v.findViewById(R.id.endWorkoutBtn).setOnClickListener(view -> {
            workoutManager.saveWorkout();
            changeFragment(new WelcomeScreen_fragment());
        });


        workoutName.setText(workoutManager.getWorkout().getName());

        initRecyclerView(v);
        return v;
    }


    @Override public void onViewCreated(View view,
                               Bundle savedInstanceState){
        TextView text = view.findViewById(R.id.workoutName);
        text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    workoutManager.changeWorkoutName(text.getText().toString());
            }

            @Override public void afterTextChanged(Editable editable) {}
        });

    }
    private void initRecyclerView(View v) {
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        exerciseAdapter = new ExerciseAdapter(getContext());
        listOfWorkouts.setAdapter(exerciseAdapter);
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void changeFragment(Fragment fragment) {
        //getParentFragmentManager().popBackStack(AddWorkoutName.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //getView().setVisibility(View.GONE);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment.getClass(), null)
                .addToBackStack(null)
                .commit();
    }


}
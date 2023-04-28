package com.example.trainer.ui.workouts.workoutStats;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Fragment to show the chosen workout's information, like the date, the duration and exercises
 */
public class WorkoutStats_fragment extends Fragment {

    private final TrainerController workoutManager;
    private TextView workoutName;
    private TextView workoutTime;
    private TextView workoutDuration;

    private Button setPreset;

    private Button share;
    private RecyclerView exercises;

    Workout workout;

    public WorkoutStats_fragment() {
        this.workoutManager = BaseController.getController();
    }


    public static WorkoutStats_fragment newInstance(Workout workout) {
        WorkoutStats_fragment fragment = new WorkoutStats_fragment();

        Bundle args = new Bundle();
        args.putSerializable(null, workout);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.workout_stats_fragment, container, false);
        workoutName = view.findViewById(R.id.workoutName);
        workoutTime = view.findViewById(R.id.workoutTime);
        workoutDuration = view.findViewById(R.id.wDuration);
        exercises = view.findViewById(R.id.exerciseRecyclerView);
        setPreset = view.findViewById(R.id.makePresetBtn);
        share = view.findViewById(R.id.shareBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        workout = (Workout) getArguments().get(null);

        Date time = workout.getWorkoutStarted();
        workoutName.setText(workout.getName());
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String str = DateFormat.format(time);
        workoutTime.setText(str);
        workoutDuration.setText(workout.getDuration());

        List<Exercise> exerciseTypes = workout.getExercises();

        WorkoutStatsExerciseAdapter adapter = new WorkoutStatsExerciseAdapter(exerciseTypes, getContext());
        exercises.setLayoutManager(new LinearLayoutManager(getContext()));
        exercises.setAdapter(adapter);

        setPreset.setOnClickListener(v -> {
            if(workout.preset()){
                Toaster.toast(getContext(), String.format(getContext().getString(R.string.alreadyPreset), workout.getName()));
            } else {
                workoutManager.makePreset(workout);
                Toaster.toast(getContext(), String.format(getContext().getString(R.string.nowPreset), workout.getName()));
            }
        });

        share.setOnClickListener(v -> {
            //TODO: to be seen
        });

    }
}
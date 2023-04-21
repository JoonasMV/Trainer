package com.example.trainer.UI.workouts.workoutStats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WorkoutStats_fragment extends Fragment {

    private TextView workoutName;
    private TextView workoutTime;
    private TextView workoutDuration;
    private RecyclerView exercises;
    Workout workout;

    public WorkoutStats_fragment() {
        // Required empty public constructor
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

    }
}
package com.example.trainer.UI.workouts;

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
import com.example.trainer.UI.exercises.exerciseChart.ExerciseChart_fragment;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WorkoutStats_fragment extends Fragment {

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
        return inflater.inflate(R.layout.workout_stats_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        workout = (Workout) getArguments().get(null);

        Date time = workout.getWorkoutStarted();

        TextView workoutName = (TextView) view.findViewById(R.id.workoutName);
        TextView workoutTime = (TextView) view.findViewById(R.id.workoutTime);
        TextView workoutDuration = (TextView) view.findViewById(R.id.wDuration);
        workoutName.setText(workout.getName());

        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String str = DateFormat.format(time);
        workoutTime.setText(str);

        workoutDuration.setText(workout.getDuration());
        RecyclerView exercises = view.findViewById(R.id.exerciseRecyclerView);

        List<Exercise> exerciseTypes = workout.getExList();


        WorkoutStatsExerciseAdapter adapter = new WorkoutStatsExerciseAdapter(exerciseTypes, getParentFragmentManager());
        exercises.setLayoutManager(new LinearLayoutManager(getContext()));
        exercises.setAdapter(adapter);

    }
}
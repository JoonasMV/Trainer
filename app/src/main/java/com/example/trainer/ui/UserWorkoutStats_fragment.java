package com.example.trainer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.ui.workouts.workoutStats.WorkoutStatsExerciseAdapter;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserWorkoutStats_fragment extends Fragment {

    private TextView workoutName;
    private TextView workoutTime;
    private TextView workoutDuration;
    private RecyclerView exercises;

    private Button save;
    Workout workout;

    public UserWorkoutStats_fragment() {
        // Required empty public constructor
    }


    public static UserWorkoutStats_fragment newInstance(Workout workout) {
        UserWorkoutStats_fragment fragment = new UserWorkoutStats_fragment();
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
        View view = inflater.inflate(R.layout.user_workout_stats_fragment, container, false);
        workoutName = view.findViewById(R.id.workoutName);
        workoutTime = view.findViewById(R.id.workoutTime);
        workoutDuration = view.findViewById(R.id.wDuration);
        exercises = view.findViewById(R.id.exerciseRecyclerView);
        save = view.findViewById(R.id.save);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        workout = (Workout) getArguments().get(null);
        Date time = workout.getWorkoutStarted();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String str = DateFormat.format(time);

        workoutName.setText(workout.getName());
        workoutTime.setText(str);
        workoutDuration.setText(workout.getDuration());

        List<Exercise> exerciseTypes = workout.getExercises();

        WorkoutStatsExerciseAdapter adapter = new WorkoutStatsExerciseAdapter(exerciseTypes, getContext());
        exercises.setLayoutManager(new LinearLayoutManager(getContext()));
        exercises.setAdapter(adapter);

        //save.setOnClickListener(v -> {
            //TODO: to be continued
        //});
    }
}

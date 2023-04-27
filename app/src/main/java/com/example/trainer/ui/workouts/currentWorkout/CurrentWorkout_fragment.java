package com.example.trainer.ui.workouts.currentWorkout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.ui.HomeScreen_fragment;
import com.example.trainer.ui.workouts.currentWorkout.adapters.ExerciseAdapter;
import com.example.trainer.ui.workouts.presetWorkouts.PresetWorkouts_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Workout;

import java.time.Duration;
import java.util.Date;

public class CurrentWorkout_fragment extends Fragment {
    private final TrainerController workoutManager = BaseController.getController();
    private TextView workoutNameText;
    private TextView workoutDurationText;
    private com.example.trainer.ui.workouts.currentWorkout.WorkoutDurationUpdater updater;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.current_workout_fragment, container, false);
        if(!workoutManager.workoutActive()) {
            changeFragment(new PresetWorkouts_fragment());
            return v;
        }

        workoutNameText = v.findViewById(R.id.workoutName);
        workoutDurationText = v.findViewById(R.id.workoutDuration);
        workoutNameText.setText(workoutManager.getWorkout().getName());

        createListeners(v);

        initRecyclerView(v);
        return v;
    }

    private void createListeners(View v){
        v.findViewById(R.id.cancelWorkoutBtn).setOnClickListener(view -> {
            workoutManager.cancelWorkout(getContext());
            changeFragment(new PresetWorkouts_fragment());
        });
        v.findViewById(R.id.addExerciseBtn).setOnClickListener(view -> changeFragment(new SelectExercise_fragment()));
        v.findViewById(R.id.endWorkoutBtn).setOnClickListener(view -> {
            workoutManager.saveWorkout();
            changeFragment(new HomeScreen_fragment());
        });
        workoutNameText.addTextChangedListener(new WorkoutNameTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                workoutManager.changeWorkoutName(workoutNameText.getText().toString());
            }
        });
    }


    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        createAndStartDurationUpdater();
    }

    private void createAndStartDurationUpdater(){
        Duration initialDuration = calculateInitialDuration();
        String initialDurationString = getDurationString(initialDuration);
        workoutDurationText.setText(initialDurationString);
        updater = new WorkoutDurationUpdater(initialDuration, duration -> {
            String durationString = getDurationString(duration);
            workoutDurationText.setText(durationString);
        });
        updater.start();
    }

    @SuppressLint("DefaultLocale")
    private String getDurationString(Duration duration){
        long hours = duration.toHours();
        long minutes = duration.toMinutes() - (hours * 60);
        long seconds = duration.getSeconds() - (duration.toMinutes() * 60);
        if(hours > 0){
            return String.format(getContext().getString(R.string.durationHourMinSec), hours, minutes, seconds);
        }
        return String.format(getContext().getString(R.string.durationMinSec), minutes, seconds);
    }

    private Duration calculateInitialDuration(){
        Date now = new Date();
        Workout active = workoutManager.getWorkout();
        Date startTime = active.getWorkoutStarted();
        long durationMs = now.getTime() - startTime.getTime();
        return Duration.ofMillis(durationMs);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(updater != null){
            updater.stopUpdating();
        }
    }

    private void initRecyclerView(View v) {
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(getContext());
        listOfWorkouts.setAdapter(exerciseAdapter);
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void changeFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment.getClass(), null)
                .addToBackStack(null)
                .commit();
    }
}
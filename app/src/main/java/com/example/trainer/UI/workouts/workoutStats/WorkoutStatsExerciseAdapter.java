package com.example.trainer.UI.workouts.workoutStats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;

import com.example.trainer.schemas.Exercise;


import java.util.List;

public class WorkoutStatsExerciseAdapter extends RecyclerView.Adapter<WorkoutStatsExerciseAdapter.ViewHolder> {

    private final List<Exercise> exercises;
    private final FragmentManager fManager;


    private Context parentContext;

    public WorkoutStatsExerciseAdapter(List<Exercise> exercises, FragmentManager fManager) {
        this.fManager = fManager;
        this.exercises = exercises;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView exerciseTypeTitle;


        public ViewHolder(View view) {
            super(view);

            exerciseTypeTitle = view.findViewById(R.id.exerciseType);
        }
    }

    @NonNull
    @Override
    public WorkoutStatsExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_type_item, parent, false);

        return new ViewHolder(v);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.exerciseTypeTitle.setText(exercise.getExerciseName());

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

}

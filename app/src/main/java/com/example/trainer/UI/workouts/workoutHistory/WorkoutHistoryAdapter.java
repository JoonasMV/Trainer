package com.example.trainer.UI.workouts.workoutHistory;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.MainActivity;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStats_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.List;


public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private final ArrayList<Workout> workoutHistory;

    private final TrainerController workoutManager;
    private Context parentContext;

    public WorkoutHistoryAdapter(List<Workout> workoutHistory) {
        this.workoutHistory = new ArrayList<>(workoutHistory);
        this.workoutManager = BaseController.getController();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;
        private final Button saveAsPresetBtn;

        private final Button deleteButton;


        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.workoutHistoryItem);
            saveAsPresetBtn = view.findViewById(R.id.saveAsPresetBtn);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public WorkoutHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parentContext)
                .inflate(R.layout.workout_history_item, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryAdapter.ViewHolder holder, int position) {
        Workout workout = workoutHistory.get(position);

        holder.workoutTitle.setText(workout.getName());
        holder.saveAsPresetBtn.setOnClickListener(view -> {
            if(workout.isPreset()){
                Toaster.toast(parentContext, String.format(parentContext.getString(R.string.alreadyPreset), workout.getName()));
            } else {
                workoutManager.makePreset(workout);
                Toaster.toast(parentContext, String.format(parentContext.getString(R.string.nowPreset), workout.getName()));
            }
        });
        holder.deleteButton.setOnClickListener(view -> {
            workoutManager.deleteWorkout(workout);
            workoutHistory.remove(workout);
            Toaster.toast(parentContext, parentContext.getString(R.string.workoutRemoved));
            notifyDataSetChanged();
        });

        holder.workoutTitle.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putSerializable(null, workout);
            ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, WorkoutStats_fragment.newInstance(workout), null)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

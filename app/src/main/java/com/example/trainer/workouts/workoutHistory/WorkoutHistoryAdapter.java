package com.example.trainer.workouts.workoutHistory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.schemas.Workout;
import com.example.trainer.util.Toaster;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

import java.util.ArrayList;
import java.util.List;


public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private ArrayList<Workout> workoutHistory;

    private WorkoutManager workoutManager;
    private Context parentContext;

    public WorkoutHistoryAdapter(List<Workout> workoutHistory) {
        this.workoutHistory = new ArrayList<>(workoutHistory);
        this.workoutManager = WorkoutManager.getInstance();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;
        private Button saveAsPresetBtn;

        private Button deleteButton;


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

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryAdapter.ViewHolder holder, int position) {
        Workout workout = workoutHistory.get(position);

        holder.workoutTitle.setText(workout.getName());
        holder.saveAsPresetBtn.setOnClickListener(view -> {
            if(workout.isPreset()){
                Toaster.toast(parentContext, String.format("%s is already a preset", workout.getName()));
            } else {
                workoutManager.makePreset(workout);
                Toaster.toast(parentContext, String.format("%s is now a preset", workout.getName()));
            }
        });
        holder.deleteButton.setOnClickListener(view -> {
            workoutManager.deleteWorkout(workout);
            workoutHistory.remove(workout);
            Toaster.toast(parentContext, "Workout removed!");
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

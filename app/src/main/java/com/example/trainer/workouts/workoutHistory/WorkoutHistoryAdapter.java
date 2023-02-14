package com.example.trainer.workouts.workoutHistory;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    List<Workout> workoutHistory;
    public WorkoutHistoryAdapter(List<Workout> workoutHistory) {
        this.workoutHistory = workoutHistory;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.workoutHistoryItem);
        }
    }

    @NonNull
    @Override
    public WorkoutHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_history_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryAdapter.ViewHolder holder, int position) {
        try {
            holder.workoutTitle.setText(workoutHistory.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

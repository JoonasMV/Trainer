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


public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    ArrayList<Workout> workoutHistory;
    public WorkoutHistoryAdapter(ArrayList<Workout> workoutHistory) {
        this.workoutHistory = workoutHistory;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.workoutHistoryTitle);
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
        holder.workoutTitle.setText(workoutHistory.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

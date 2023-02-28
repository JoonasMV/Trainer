package com.example.trainer.workouts.workoutHistory;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private ArrayList<Workout> workoutHistory;
    private WorkoutDAO workoutDAO;

    public WorkoutHistoryAdapter(List<Workout> workoutHistory) {
        this.workoutHistory = new ArrayList<>(workoutHistory);
        this.workoutDAO = new WorkoutDAO();
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_history_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryAdapter.ViewHolder holder, int position) {
        Workout workout = workoutHistory.get(position);

        holder.workoutTitle.setText(workout.getName());
        holder.saveAsPresetBtn.setOnClickListener(view -> workoutDAO.makePreset(workout));
        holder.deleteButton.setOnClickListener(view -> {
            workoutDAO.delete(workout);
            workoutHistory.remove(workout);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

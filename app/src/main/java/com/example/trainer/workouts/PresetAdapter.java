package com.example.trainer.workouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.List;

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.ViewHolder> {

    private ArrayList<Workout> workoutPresets;
    private WorkoutDAO workoutDAO;

    public PresetAdapter(List<Workout> workoutPresets) {
        this.workoutPresets = new ArrayList<>(workoutPresets);
        this.workoutDAO = new WorkoutDAO();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.preset);
        }
    }

    @NonNull
    @Override
    public PresetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preset_item, parent, false);

        return new PresetAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetAdapter.ViewHolder holder, int position) {

        holder.workoutTitle.setText(workoutPresets.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return workoutPresets.size();
    }
}

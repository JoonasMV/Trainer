package com.example.trainer.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.util.Toaster;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;
import com.example.trainer.workouts.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.ViewHolder> {

    private ArrayList<Workout> workoutPresets;
    private WorkoutDAO workoutDAO;
    private FragmentManager fManager;

    private Context parentContext;

    public PresetAdapter(List<Workout> workoutPresets, FragmentManager fManager) {
        this.fManager = fManager;
        this.workoutPresets = new ArrayList<>(workoutPresets);
        this.workoutDAO = new WorkoutDAO();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        private Button deleteButton;
        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.preset);
            deleteButton = view.findViewById(R.id.deletePresetButton);
        }
    }

    @NonNull
    @Override
    public PresetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preset_item, parent, false);

        return new PresetAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetAdapter.ViewHolder holder, int position) {
        Workout workout = workoutPresets.get(position);

        holder.workoutTitle.setText(workout.getName());
        holder.workoutTitle.setOnClickListener(view -> {
            WorkoutManager.getInstance().startWorkoutFromPreset(workout);

            fManager.beginTransaction().replace(R.id.mainContainer, new CurrentWorkoutFragment()).commit();
        });

        holder.deleteButton.setOnClickListener(view -> {
            workoutDAO.delete(workout);
            workoutPresets.remove(workout);
            Toaster.toast(parentContext, "Preset removed!");
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return workoutPresets.size();
    }
}

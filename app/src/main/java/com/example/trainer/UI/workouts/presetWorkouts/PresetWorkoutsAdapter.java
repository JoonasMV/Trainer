package com.example.trainer.UI.workouts.presetWorkouts;

import android.annotation.SuppressLint;
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
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Workout;
import com.example.trainer.UI.workouts.currentWorkout.CurrentWorkout_fragment;
import com.example.trainer.util.Toaster;

import java.util.List;

public class PresetWorkoutsAdapter extends RecyclerView.Adapter<PresetWorkoutsAdapter.ViewHolder> {

    private final List<Workout> presets;
    private final FragmentManager fManager;
    private final TrainerController workoutManager = BaseController.getController();

    private Context parentContext;

    public PresetWorkoutsAdapter(List<Workout> presets, FragmentManager fManager) {
        this.fManager = fManager;
        this.presets = presets;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        private final Button deleteButton;
        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.preset);
            deleteButton = view.findViewById(R.id.deletePresetButton);
        }
    }

    @NonNull
    @Override
    public PresetWorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preset_workout_item, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull PresetWorkoutsAdapter.ViewHolder holder, int position) {
        Workout workout = presets.get(position);

        holder.workoutTitle.setText(workout.getName());
        holder.workoutTitle.setOnClickListener(view -> {
            workoutManager.startWorkoutFromPreset(workout);
            fManager.beginTransaction().replace(R.id.mainContainer, new CurrentWorkout_fragment()).commit();
        });

        holder.deleteButton.setOnClickListener(view -> {
            workoutManager.deleteWorkout(workout);
            presets.remove(workout);
            Toaster.toast(parentContext, parentContext.getString(R.string.presetRemoved));
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return presets.size();
    }
}

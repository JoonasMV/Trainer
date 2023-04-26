package com.example.trainer.ui.workouts.presetWorkouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.ui.UpdatableAdapter;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Workout;
import com.example.trainer.ui.workouts.currentWorkout.CurrentWorkout_fragment;
import com.example.trainer.util.Toaster;

import java.util.Collections;
import java.util.List;

public class PresetWorkoutsAdapter extends UpdatableAdapter<List<Workout>, PresetWorkoutsAdapter.ViewHolder> {

    private List<Workout> presets;
    private final FragmentManager fManager;
    private final TrainerController workoutManager = BaseController.getController();

    private Context parentContext;

    public PresetWorkoutsAdapter(FragmentManager fManager) {
        this.fManager = fManager;
        this.presets = Collections.emptyList();
    }

    @Override
    public void update(List<Workout> data) {
        this.presets = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        private final ImageButton deleteButton;
        public ViewHolder(View view) {
            super(view);
            workoutTitle = view.findViewById(R.id.preset);
            deleteButton = view.findViewById(R.id.button);
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

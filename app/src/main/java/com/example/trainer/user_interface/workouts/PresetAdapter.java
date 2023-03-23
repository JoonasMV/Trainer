package com.example.trainer.user_interface.workouts;

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
import com.example.trainer.schemas.Workout;
import com.example.trainer.user_interface.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.util.Toaster;

import java.util.List;

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.ViewHolder> {

    private final List<Workout> presets;
    private final FragmentManager fManager;
    private final TrainerController workoutManager = BaseController.getController();

    private Context parentContext;

    public PresetAdapter(List<Workout> presets, FragmentManager fManager) {
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
    public PresetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preset_item, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull PresetAdapter.ViewHolder holder, int position) {
        Workout workout = presets.get(position);
        System.out.println(workout.getExList().size());

        holder.workoutTitle.setText(workout.getName());
        holder.workoutTitle.setOnClickListener(view -> {
            workoutManager.startWorkoutFromPreset(workout);
            fManager.beginTransaction().replace(R.id.mainContainer, new CurrentWorkoutFragment()).commit();
        });

        holder.deleteButton.setOnClickListener(view -> {
            workoutManager.deleteWorkout(workout);
            presets.remove(workout);
            Toaster.toast(parentContext, "Preset removed!");
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return presets.size();
    }
}

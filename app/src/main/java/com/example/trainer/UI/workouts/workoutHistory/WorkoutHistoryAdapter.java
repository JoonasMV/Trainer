package com.example.trainer.UI.workouts.workoutHistory;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.MainActivity;

import com.example.trainer.UI.UpdatableAdapter;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStats_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WorkoutHistoryAdapter extends UpdatableAdapter<List<Workout>, WorkoutHistoryAdapter.ViewHolder> {

    private List<Workout> workoutHistory;

    private final TrainerController workoutManager;
    private Context parentContext;

    private PopupMenu ppMenu;

    public WorkoutHistoryAdapter() {
        this.workoutHistory = Collections.emptyList();
        this.workoutManager = BaseController.getController();
    }

    @Override
    public void update(List<Workout> data) {
        workoutHistory = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;
        private final ImageButton deleteButton;

        private final ImageButton options;

        public ViewHolder(View view) {
            super(view);
            workoutTitle = view.findViewById(R.id.workoutHistoryItem);
            deleteButton = view.findViewById(R.id.deleteButton);
            options = view.findViewById(R.id.optionsButton);
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
        System.out.println(workout);

        holder.workoutTitle.setText(workout.getName());

        holder.deleteButton.setOnClickListener(view -> {
            workoutManager.deleteWorkout(workout);
            workoutHistory.remove(workout);
            Toaster.toast(parentContext, parentContext.getString(R.string.workoutRemoved));
            notifyDataSetChanged();
        });

        holder.workoutTitle.setOnClickListener(view -> {
            System.out.println(workout);
            Bundle args = new Bundle();
            args.putSerializable(null, workout);
            ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, WorkoutStats_fragment.newInstance(workout), null)
                    .addToBackStack(null).commit();
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                ppMenu = new PopupMenu(view.getContext(), holder.options );
                ppMenu.getMenuInflater().inflate(R.menu.workout_popup_menu, ppMenu.getMenu());
                ppMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.showWorkout:
                            Bundle args = new Bundle();
                            args.putSerializable(null, workout);
                            ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainContainer, WorkoutStats_fragment.newInstance(workout), null)
                                    .addToBackStack(null).commit();
                            break;
                        case R.id.saveAsPreset:
                            if(workout.preset()){
                                Toaster.toast(parentContext, String.format(parentContext.getString(R.string.alreadyPreset), workout.getName()));
                            } else {
                                workoutManager.makePreset(workout);
                                Toaster.toast(parentContext, String.format(parentContext.getString(R.string.nowPreset), workout.getName()));
                            }
                            break;
                        case R.id.share:
                            //TODO: sharing is coming
                            break;
                    }
                    return true;
                });
                ppMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistory.size();
    }
}

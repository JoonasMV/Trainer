package com.example.trainer.UI;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStatsExerciseAdapter;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStatsSetAdapter;
import com.example.trainer.UI.workouts.workoutStats.WorkoutStats_fragment;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseSet;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder> {

    private final List<Workout> workouts;

    private Context parentContext;


    private PopupMenu ppMenu;

    public UserProfileAdapter(List<Workout> workouts, Context parentContext) {
        this.workouts = workouts;
        this.parentContext = parentContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;

        public ImageButton button;


        boolean isPressed;
        public ViewHolder(View view) {
            super(view);

            workoutTitle = view.findViewById(R.id.workoutText);
            button = view.findViewById(R.id.optionsButton);
        }
    }

    @NonNull
    @Override
    public UserProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parentContext)
                .inflate(R.layout.shared_workout_item, parent, false);

        return new UserProfileAdapter.ViewHolder(v);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.ViewHolder holder, int position) {

        Workout workout = workouts.get(position);

        holder.workoutTitle.setText(workout.getName());

        holder.workoutTitle.setOnClickListener(view -> {
            showWorkout(view, workout);
        });

        holder.button.setOnClickListener(view -> {
            ppMenu = new PopupMenu(view.getContext(), holder.button );
            ppMenu.getMenuInflater().inflate(R.menu.user_popup_menu, ppMenu.getMenu());
            ppMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.show:
                        showWorkout(view, workout);
                        break;
                    case R.id.save:
                        //TODO: to be seen
                        break;
                }
                return true;
            });
            ppMenu.show();
        });


        }


    private void showWorkout(View view, Workout workout) {
        Bundle args = new Bundle();
        args.putSerializable(null, workout);
        ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, UserWorkoutStats_fragment.newInstance(workout), null)
                .addToBackStack(null).commit();
    }
    @Override
    public int getItemCount() {
        return workouts.size();
    }

}

package com.example.trainer.UI.workouts.currentWorkout.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private final TrainerController workoutManager = BaseController.getController();
    private final Context context;

    public ExerciseAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameOfTheExercise;
        private final RecyclerView listOfSets;
        private final Button addSetButton;


        public ViewHolder(View view) {
            super(view);

            nameOfTheExercise = view.findViewById(R.id.nameOfTheExercise);
            listOfSets = view.findViewById(R.id.listOfSets);
            addSetButton = view.findViewById(R.id.addSetBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_exercise_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfTheExercise.setText(workoutManager.getWorkout().getExercises().get(position).getExerciseName());

        SetAdapter setAdapter = new SetAdapter(workoutManager.getWorkout().getExercises().get(position), context);
        holder.listOfSets.setAdapter(setAdapter);
        holder.listOfSets.setLayoutManager(new LinearLayoutManager(context));

        holder.addSetButton.setOnClickListener(view -> {
            //TODO: notifyDataSetChanged() does not invoke animations
            workoutManager.addSet(position);
            setAdapter.notifyDataSetChanged();
        });

    }
    @Override
    public int getItemCount() {
        return workoutManager.getWorkout().getExercises().size();
    }
}

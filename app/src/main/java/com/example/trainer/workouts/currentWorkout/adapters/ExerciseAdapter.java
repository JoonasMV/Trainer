package com.example.trainer.workouts.currentWorkout.adapters;

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
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private final WorkoutManager workoutManager = WorkoutManager.getInstance();
    private Context context;

    public ExerciseAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_exercise, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfTheExercise.setText(workoutManager.getWorkout().getExList().get(position).getExerciseName());

        SetAdapter setAdapter = new SetAdapter(workoutManager.getWorkout().getExList().get(position), context);
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
        return workoutManager.getWorkout().getExList().size();
    }
}

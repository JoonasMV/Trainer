package com.example.trainer.UI.workouts.workoutStats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.model.ExerciseSet;


import java.util.List;

public class WorkoutStatsSetAdapter extends RecyclerView.Adapter<WorkoutStatsSetAdapter.ViewHolder> {

    private final List<ExerciseSet> sets;


    private Context parentContext;

    public WorkoutStatsSetAdapter(List<ExerciseSet> sets, Context parentContext) {
        this.parentContext = parentContext;
        this.sets = sets;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Reps;
        public TextView Weight;

        public ViewHolder(View view) {
            super(view);

            Reps = view.findViewById(R.id.textReps);
            Weight = view.findViewById(R.id.textWeight);
        }
    }

    @NonNull
    @Override
    public WorkoutStatsSetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_set_item, parent, false);

        return new WorkoutStatsSetAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull WorkoutStatsSetAdapter.ViewHolder holder, int position) {
        ExerciseSet set = sets.get(position);
        System.out.println("Toistot "+set.getReps());
        System.out.println("Paino "+set.getWeight());

        int repsInt = set.getReps();
        double weightDouble = set.getWeight();

        String reps;
        String weight;
        if(repsInt < 0){
            reps = "-";
        } else {
            reps = String.valueOf(repsInt);
        }
        if(weightDouble < 0){
            weight = "-";
        } else{
            weight = String.valueOf(weightDouble);
        }
        holder.Reps.setText(reps);
        holder.Weight.setText(weight);
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

}

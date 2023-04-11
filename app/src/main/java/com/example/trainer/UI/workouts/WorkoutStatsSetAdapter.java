package com.example.trainer.UI.workouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;

import java.util.List;
import java.util.Set;

public class WorkoutStatsSetAdapter extends RecyclerView.Adapter<WorkoutStatsSetAdapter.ViewHolder> {

    private final List<ExerciseSet> sets;
    private final FragmentManager fManager;


    private Context parentContext;

    public WorkoutStatsSetAdapter(List<ExerciseSet> sets, FragmentManager fManager) {
        this.fManager = fManager;
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
                .inflate(R.layout.exercise_type_item, parent, false);

        return new WorkoutStatsSetAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull WorkoutStatsSetAdapter.ViewHolder holder, int position) {
        ExerciseSet set = sets.get(position);
        System.out.println("Toistot "+set.getAmount());
        System.out.println("Paino "+set.getWeight());

        holder.Reps.setText(set.getAmount());
        holder.Weight.setText((int) set.getWeight());

    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

}

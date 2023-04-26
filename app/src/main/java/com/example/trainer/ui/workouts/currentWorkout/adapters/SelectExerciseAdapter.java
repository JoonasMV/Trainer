package com.example.trainer.ui.workouts.currentWorkout.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.ui.MainActivity;
import com.example.trainer.ui.workouts.currentWorkout.CurrentWorkout_fragment;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;

import java.util.List;

public class SelectExerciseAdapter extends RecyclerView.Adapter<SelectExerciseAdapter.ViewHolder> {

    private final Context context;
    private List<ExerciseType> types;

    private TrainerController controller;



    public SelectExerciseAdapter(List<ExerciseType> types,  Context context, TrainerController manager) {
        this.context = context;
        this.types = types;
        this.controller = manager;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameOfTheExercise;


        public ViewHolder(View view) {
            super(view);
            nameOfTheExercise = view.findViewById(R.id.nameOfType);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_exercise_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfTheExercise.setText(types.get(position).getName());

        holder.nameOfTheExercise.setOnClickListener(view -> {
            if(!types.isEmpty()){
                Exercise newExercise = new Exercise();
                newExercise.setExerciseType(types.get(position));

                //TODO: add workout id to exercise when saving workout
                controller.addExercise(newExercise);
                Fragment f = new CurrentWorkout_fragment();
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainContainer, f,"")
                        .addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return types.size();
    }

}

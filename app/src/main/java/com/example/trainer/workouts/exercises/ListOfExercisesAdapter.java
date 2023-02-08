package com.example.trainer.workouts.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ListOfExercisesAdapter extends RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder> {

    private ArrayList<Exercise> exerciseList;

    public ListOfExercisesAdapter(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfExercise;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.nameOfExercise);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfExercise.setText(exerciseList.get(position).getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}

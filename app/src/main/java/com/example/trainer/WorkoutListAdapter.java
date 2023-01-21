package com.example.trainer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> exerciseList;

    public WorkoutListAdapter(Context context, ArrayList<String> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public WorkoutListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workout_list_view, parent, false);
        return new WorkoutListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutListAdapter.MyViewHolder holder, int position) {

        holder.exerciseField.setText(exerciseList.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseField;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseField = itemView.findViewById(R.id.exerciseField);
        }
    }
}

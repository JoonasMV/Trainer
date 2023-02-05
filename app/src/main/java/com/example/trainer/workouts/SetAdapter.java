package com.example.trainer.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Exercise;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    private Exercise exercise;
    private Context context;

    public SetAdapter(Exercise exercise, Context context) {
        this.exercise = exercise;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView setCounter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setCounter = itemView.findViewById(R.id.setCounter);
        }
    }


    @NonNull
    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.single_set, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.ViewHolder holder, int position) {
        holder.setCounter.setText(Integer.toString(position+1));
    }

    @Override
    public int getItemCount() {
        return exercise.getSetList().size();
    }
}

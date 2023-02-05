package com.example.trainer.workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Workout;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private Workout parentItem;
    private Context context;

    public ExerciseAdapter(Workout workout, Context context) {
        this.parentItem = workout;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfTheExerciseASD;
        private RecyclerView listOfSets;

        public ViewHolder(View view) {
            super(view);

            nameOfTheExerciseASD = view.findViewById(R.id.nameOfTheExerciseASD);
            listOfSets = view.findViewById(R.id.listOfSets);
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
        holder.nameOfTheExerciseASD.setText(parentItem.getExList().get(position).getExerciseName());
        holder.listOfSets.setAdapter(new SetAdapter(
                parentItem.getExList().get(position),
                context)
        );
        holder.listOfSets.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return parentItem.getExList().size();
    }
}

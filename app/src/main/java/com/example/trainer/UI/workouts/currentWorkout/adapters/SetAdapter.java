package com.example.trainer.UI.workouts.currentWorkout.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;

import java.text.DecimalFormat;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    private Exercise exercise;
    private Context context;
    private DecimalFormat df = new DecimalFormat("###.#");

    public SetAdapter(Exercise exercise, Context context) {
        this.exercise = exercise;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView setCounter;
        private EditText setRepField;
        private EditText setWeightField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setCounter = itemView.findViewById(R.id.setCounter);
            setRepField = itemView.findViewById(R.id.setRepField);
            setWeightField = itemView.findViewById(R.id.setWeightField);
        }
    }


    @NonNull
    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.single_set_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.ViewHolder holder, int position) {
        holder.setCounter.setText(Integer.toString(position + 1));

        ExerciseSet valuePosition = exercise.getSets().get(holder.getAdapterPosition());

        try {
            holder.setRepField.setText(String.valueOf(valuePosition.getReps()));
            if (valuePosition.getReps() <= -1) {
                holder.setRepField.setText(null);
                holder.setRepField.setHint("Reps");
            }
        } catch (NumberFormatException e) {
            holder.setRepField.setHint("Reps");
        }

        try {
            holder.setWeightField.setText(String.valueOf(valuePosition.getWeight()));
            if (valuePosition.getWeight() <= -1) {
                holder.setWeightField.setText(null);
                holder.setWeightField.setHint("Weight");
            }
        } catch (NumberFormatException e) {
            holder.setWeightField.setHint("Weight");
        }


        holder.setRepField.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    valuePosition.setReps(Integer.parseInt(holder.setRepField.getText().toString()));
                } catch (NumberFormatException e) {
                    valuePosition.setReps(-1);
//                    holder.setRepField.setError("Invalid rep amount");
                }
            }

            @Override public void afterTextChanged(Editable editable) {}
        });

        holder.setWeightField.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    valuePosition.setWeight(Double.parseDouble(holder.setWeightField.getText().toString()));
                } catch (NumberFormatException e) {
                    valuePosition.setWeight(-1);
//                    holder.setWeightField.setError("Invalid weight amount");
                }
            }

            @Override public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public int getItemCount() {
        return exercise.getSets().size();
    }
}

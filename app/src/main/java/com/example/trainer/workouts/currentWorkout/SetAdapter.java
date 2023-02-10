package com.example.trainer.workouts.currentWorkout;

import android.content.Context;
import android.content.Intent;
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
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;

import java.text.DecimalFormat;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    private Exercise exercise;
    private Context context;
    DecimalFormat df = new DecimalFormat("###.#");

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
                .inflate(R.layout.single_set, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAdapter.ViewHolder holder, int position) {
        holder.setCounter.setText(Integer.toString(position+1));
        ExerciseSet currentSet = exercise.getSetList().get(position);

        try {
            holder.setWeightField.setText(String.valueOf(exercise.getSetList().get(holder.getAdapterPosition()).getWeight()));
            holder.setRepField.setText(String.valueOf(exercise.getSetList().get(holder.getAdapterPosition()).getAmount()));
        } catch (Exception e) {
        }

        //TODO: error checking
        holder.setWeightField.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               try {
                exercise.getSetList().get(holder.getAdapterPosition()).setWeight(Double.parseDouble(holder.setWeightField.getText().toString()));
               } catch (Exception e) {

               }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        holder.setRepField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    exercise.getSetList().get(holder.getAdapterPosition()).setAmount(Integer.parseInt(holder.setRepField.getText().toString()));
                } catch (Exception e) {

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return exercise.getSetList().size();
    }

}

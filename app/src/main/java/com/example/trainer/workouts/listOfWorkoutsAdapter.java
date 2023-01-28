package com.example.trainer.workouts;

import static java.lang.Integer.valueOf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;

public class listOfWorkoutsAdapter extends RecyclerView.Adapter<listOfWorkoutsAdapter.ViewHolder> {

    private String[] localDataSet = {"1", "2", "3"};
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView setCounter;

        public ViewHolder(View view) {
            super(view);

            setCounter = (TextView) view.findViewById(R.id.setCounter);
        }

        public TextView getSetCounter() {
            return setCounter;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_working_exercise, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setCounter.setText(localDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

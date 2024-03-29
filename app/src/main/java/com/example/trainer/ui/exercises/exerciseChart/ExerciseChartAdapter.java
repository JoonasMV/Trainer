package com.example.trainer.ui.exercises.exerciseChart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.model.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter for the exercise type data that is shown in the ResyclerView of ExerciseChart_fragment
 */
public class ExerciseChartAdapter extends RecyclerView.Adapter<ExerciseChartAdapter.ViewHolder> {

    private ArrayList<Workout> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView weight;

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            weight = (TextView)  view.findViewById(R.id.weight);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getWeight() {
            return weight;
        }
    }


    public ExerciseChartAdapter(List<Workout> dataSet, FragmentManager fManager) {
        localDataSet = new ArrayList<>(dataSet);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exercise_chart_item, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Workout workout = localDataSet.get(position);
        Date time = workout.getWorkoutStarted();
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String strName = Integer.toString(position + 1);
        String strDate = DateFormat.format(time);

        strName += ". " + workout.getName();


        viewHolder.getWeight().setText(strDate);
        viewHolder.getDate().setText(strName);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


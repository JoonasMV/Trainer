package com.example.trainer.ui.exercises.exerciseChart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseSet;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.Workout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Shows the progress of the chosen exercise type
 */
public class ExerciseChart_fragment extends Fragment {

    TextView name;
    RecyclerView presets;
    LineChart mpLineChart;
    ExerciseType exerciseType;

    int[] minMax = new int[2];

    List<Workout> workouts;

    public static ExerciseChart_fragment newInstance(ExerciseType exerciseType) {
        ExerciseChart_fragment fragment = new ExerciseChart_fragment();

        Bundle args = new Bundle();
        args.putSerializable(null, exerciseType);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Creates a List of entries for the chart
     * entries are 5 most recent exercises' volume (weight * reps)
     * @return  returns the created ArrayList of entries
     */
    private List<Entry> dataValues1(){
        List<Entry> dataVals = new ArrayList<Entry>();

        int index = 0;
        if(workouts.size() > 5){
            index = workouts.size() - 5;
            minMax[0] = index + 1;
            minMax[1] = workouts.size();
        }

        for(int i = index; i < workouts.size(); i++){
            Workout w = workouts.get(i);
            Exercise e = new Exercise();
            for(Exercise ex : w.getExercises()){
                if(ex.getExerciseName().equals(exerciseType.getName())){
                    e = ex;
                }
            }
            double volume = calculateVolume(e);
            dataVals.add(new Entry(i + 1, (int)volume));
        }
        return dataVals;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        exerciseType = (ExerciseType) getArguments().get(null);
        System.out.println("EX TYPE " + exerciseType);
        Thread thread = new Thread(() -> {
            workouts = BaseController.getController().getAllWorkouts();
            List<Workout> sorted = sortWithExType(workouts, exerciseType);
            System.out.println("\n");
            for(Workout w : sorted){
                System.out.println(w.getWorkoutStarted());
            }
            workouts = sorted;

        });

        thread.start();
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        View v = inflater.inflate(R.layout.exercise_chart_fragment, container, false);
        mpLineChart = (LineChart) v.findViewById(R.id.lineChart);
        mpLineChart.getDescription().setEnabled(false);
        name = (TextView) v.findViewById(R.id.textView);
        name.setText(exerciseType.getName());


        LineDataSet set1 = new LineDataSet(dataValues1(), getString(R.string.volume));
        chartStyling(set1);


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        AxisBase yAxis = mpLineChart.getAxisLeft();
        AxisBase xAxis = mpLineChart.getXAxis();
        xAxis.setLabelCount(5);
        xAxis.setAxisMinimum(minMax[0]);
        xAxis.setAxisMaximum(minMax[1] + 1);
        axisStyling(yAxis);
        axisStyling(xAxis);

        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.setExtraOffsets(7,10,7,5);
        mpLineChart.setData(data);
        mpLineChart.animateY(1000);
        mpLineChart.invalidate();
        return v;
    }

    /**
     * Styles the given set of data for the chart
     * @param set1  set of data to be styled
     */
    private void chartStyling(LineDataSet set1){
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                set1.setValueTextColor(getContext().getColor(R.color.text_color_dark));
                set1.setColor(getContext().getColor(R.color.diagram_fill_color_dark));
                set1.setCircleColor(getContext().getColor(R.color.diagram_fill_color_dark));
                set1.setFillColor(getContext().getColor(R.color.diagram_fill_color_dark));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                set1.setValueTextColor(getContext().getColor(R.color.text_color_light));
                set1.setColor(getContext().getColor(R.color.diagram_fill_color_light));
                set1.setFillColor(getContext().getColor(R.color.diagram_fill_color_light));
                set1.setCircleColor(getContext().getColor(R.color.diagram_fill_color_light));
                break;
        }
        set1.setLineWidth(3f);
        set1.setCircleRadius(5f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(16f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(2f);
        set1.setFormSize(15.f);
    }


    /**
     * Styles the given axis of the chart
     * @param axis  the axis that is styled
     */
    private void axisStyling(AxisBase axis){
        axis.setTextSize(16f);
        axis.setAxisLineWidth(2f);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                axis.setTextColor(getContext().getColor(R.color.text_color_dark));
                axis.setAxisLineColor(getContext().getColor(R.color.diagram_fill_color_dark));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                axis.setTextColor(getContext().getColor(R.color.text_color_light));
                axis.setAxisLineColor(getContext().getColor(R.color.text_color_light));
                break;
        }
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        presets = view.findViewById(R.id.bestWeights);
        System.out.println("MAKE ADAPTER");
        ExerciseChartAdapter adapter = new ExerciseChartAdapter(workouts, getParentFragmentManager());
        presets.setLayoutManager(new LinearLayoutManager(getContext()));
        presets.setAdapter(adapter);

    }

    /**
     * gets workouts that contain the given exercise
     * @param workouts list of workouts
     * @param type exersisetype
     * @return list of workouts containing exercise of exercisetype
     */
    private List<Workout> sortWithExType(List<Workout> workouts, ExerciseType type){
        List<Workout> workoutsContainingEx = new ArrayList<>();
        for(Workout w : workouts){
            List<Exercise> exercises = w.getExercises();

            boolean contains = false;
            int index = 0;

            while(!contains && index < exercises.size()){
                if(exercises.get(index).getExerciseName().equals(type.getName())){
                    contains = true;
                }
                index++;
            }
            if(contains){
                workoutsContainingEx.add(w);
            }
        }

        for(Workout w : workoutsContainingEx){
            System.out.println(w.getWorkoutStarted());
        }

        quickSort(workoutsContainingEx, 0, workoutsContainingEx.size() - 1);

        System.out.println("\n");

        for(Workout w : workoutsContainingEx){
            System.out.println(w.getWorkoutStarted());
        }
        return workoutsContainingEx;



    }


    /**
     * quicksort for sorting workouts by date
     */
    private void quickSort(List<Workout> workouts, int low, int high){
        if(low >= high){
            return;
        }
        int pivotIndex = new Random().nextInt((high - low)) + low;
        swap(workouts, pivotIndex, high);

        int mid = partition(workouts, low, high);

        quickSort(workouts, low, mid - 1);
        quickSort(workouts, mid + 1, high);


    }

    /**
     * partition method for quicksort
     * @param workouts
     * @param low
     * @param high
     * @return mid index
     */
    private int partition(List<Workout> workouts, int low, int high){
        Workout pivot = workouts.get(high);
        int lowIndex = low;
        int highIndex = high;

        while (lowIndex < highIndex){
            while ((workouts.get(lowIndex).getWorkoutStarted().compareTo(pivot.getWorkoutStarted()) <= 0) && lowIndex < highIndex){
                lowIndex++;
            }
            while ((workouts.get(highIndex).getWorkoutStarted().compareTo(pivot.getWorkoutStarted()) >= 0) && lowIndex < highIndex){
                highIndex--;
            }
            swap(workouts, lowIndex, highIndex);
        }
        swap(workouts, lowIndex, high);
        return lowIndex;
    }

    /**
     * swap mehtod for quicksort
     * @param workouts
     * @param i
     * @param j
     */
    private void swap(List<Workout> workouts, int i, int j){
        Workout temp = workouts.get(i);
        workouts.set(i, workouts.get(j));
        workouts.set(j, temp);
    }

    /**
     * calculates the volume for given exercise
     * @param exercise
     * @return total volume weight * reps for all sets added up
     */
    private double calculateVolume(Exercise exercise){
        double totalVolume = 0;
        for(ExerciseSet set : exercise.getSets()){
            totalVolume += set.getWeight() * set.getReps();
        }
        return totalVolume;
    }


}
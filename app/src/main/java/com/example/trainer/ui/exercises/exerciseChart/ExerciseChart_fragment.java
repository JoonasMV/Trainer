package com.example.trainer.ui.exercises.exerciseChart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.diagram.api.model.ExerciseType;
import com.example.trainer.diagram.api.model.Workout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Shows the progress of the chosen exercise type
 */
public class ExerciseChart_fragment extends Fragment {

    TextView name;
    RecyclerView presets;
    LineChart mpLineChart;
    ExerciseType exerciseType;

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
    //placeholders
    //TODO: real values

    /**
     * Creates a List of entries for the chart
     * @return  returns the created ArrayList of entries
     */
    private List<Entry> dataValues1(){
        List<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0,20));
        dataVals.add(new Entry(1,24));
        dataVals.add(new Entry(2,2));
        dataVals.add(new Entry(3,30));
        dataVals.add(new Entry(4,38));
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

        View v = inflater.inflate(R.layout.exercise_chart_fragment, container, false);
        mpLineChart = (LineChart) v.findViewById(R.id.lineChart);
        mpLineChart.getDescription().setEnabled(false);
        name = (TextView) v.findViewById(R.id.textView);
        name.setText(exerciseType.getName());

        LineDataSet set1 = new LineDataSet(dataValues1(), getString(R.string.weight));
        chartStyling(set1);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        AxisBase yAxis = mpLineChart.getAxisLeft();
        AxisBase xAxis = mpLineChart.getXAxis();
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
        List<Workout> list = new ArrayList<>();
        //placeholders
        //TODO: real values
        list.add(new Workout("workout", new Date(), new Date()));
        list.add(new Workout("workout2", new Date(), new Date()));
        list.add(new Workout("workout3", new Date(), new Date()));

        ExerciseChartAdapter adapter = new ExerciseChartAdapter(list, getParentFragmentManager());
        presets.setLayoutManager(new LinearLayoutManager(getContext()));
        presets.setAdapter(adapter);

    }

}
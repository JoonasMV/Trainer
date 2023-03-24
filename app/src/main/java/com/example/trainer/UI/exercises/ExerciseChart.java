package com.example.trainer.UI.exercises;

import android.graphics.Color;
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
import com.example.trainer.schemas.Workout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExerciseChart extends Fragment {

    TextView name;
    RecyclerView presets;
    LineChart mpLineChart;
    ExerciseManager exerciseManager;

    public ExerciseChart() {
        exerciseManager = ExerciseManager.getInstance();
    }


    public static ExerciseChart newInstance() {
        ExerciseChart fragment = new ExerciseChart();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    //placeholders
    private ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
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

        View v = inflater.inflate(R.layout.exercise_chart_fragment, container, false);
        mpLineChart = (LineChart) v.findViewById(R.id.lineChart);
        name = (TextView) v.findViewById(R.id.textView);
        name.setText(exerciseManager.getExerciseType().getName());
        LineDataSet set1 = new LineDataSet(dataValues1(), "Weights");
        chartStyling(set1);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        AxisBase yAxis = mpLineChart.getAxisLeft();
        AxisBase xAxis = mpLineChart.getXAxis();
        yAxis.setTextSize(14f);
        yAxis.setAxisLineWidth(2f);
        xAxis.setTextSize(14f);
        xAxis.setAxisLineWidth(2f);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.setExtraOffsets(10,15,5,5);
        mpLineChart.setData(data);
        mpLineChart.animateY(1000);

        mpLineChart.invalidate();
        return v;
    }

    public void chartStyling(LineDataSet set1){
        set1.setColor(Color.rgb(28, 52, 86));
        set1.setCircleColor(Color.rgb(28, 52, 86));
        set1.setLineWidth(3f);
        set1.setCircleRadius(5f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(16f);
        set1.setDrawFilled(true);
        set1.setFillColor(Color.rgb(28, 52, 86));
        set1.setFormLineWidth(2f);

        set1.setFormSize(15.f);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        presets = view.findViewById(R.id.bestWeights);

        List<Workout> list = new ArrayList<>();

        //placeholders
        list.add(new Workout("workout", new Date(), new Date()));
        list.add(new Workout("workout2", new Date(), new Date()));
        list.add(new Workout("workout3", new Date(), new Date()));

        ExerciseChartAdapter adapter = new ExerciseChartAdapter(list, getParentFragmentManager());
        presets.setLayoutManager(new LinearLayoutManager(getContext()));
        presets.setAdapter(adapter);

    }

}
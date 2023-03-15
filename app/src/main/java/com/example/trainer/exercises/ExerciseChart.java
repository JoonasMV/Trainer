package com.example.trainer.exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class ExerciseChart extends Fragment {



    LineChart mpLineChart;
    public ExerciseChart() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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

        View v = inflater.inflate(R.layout.fragment_exercise_chart, container, false);
        mpLineChart = (LineChart) v.findViewById(R.id.linechart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "data set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);

        mpLineChart.setData(data);
        mpLineChart.invalidate();
        return v;
    }
}
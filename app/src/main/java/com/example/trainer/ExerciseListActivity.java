package com.example.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ExerciseListActivity extends Activity {

    private ArrayList lista = new ArrayList<String>(); //testilista



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lista.add("yksi");
        lista.add("kaksi");
        lista.add("kome");

        setContentView(R.layout.activity_exercise);

        Button addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(view -> {
            //next activity
        });



        ListView lv = findViewById(R.id.exerciseList);

        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lista
        ));

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag","onclick");
            //put here next activity
        });
    }



}

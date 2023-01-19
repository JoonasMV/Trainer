package com.example.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ExerciseListActivity extends Activity {


    private ExerciseDAO db = new ExerciseDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Exercise> list = db.getAllExercises();
        ArrayList<String> names = new ArrayList<>();

        for(Exercise e : list) {
            names.add(e.getName());
        }
        setContentView(R.layout.activity_exercise);

        Button addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(view -> {
            startActivity(new Intent(ExerciseListActivity.this, NewExerciseActivity.class));
        });



        ListView lv = findViewById(R.id.exerciseList);


        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names
        ));



        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag","onclick");
            //put here next activity
        });
    }



}

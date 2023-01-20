package com.example.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity {



    private ExerciseDAO db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new ExerciseDAO(this);
        db.addTestExercises();
        ArrayList<Exercise> list = db.getAllExercises();
        ArrayList<String> names = new ArrayList<>();

        for(Exercise e : list){
            List<ExerciseSet> sets = e.getSets();
            for (ExerciseSet ex : sets){
                Log.d("SET", ex.toString());
            }
        }

        ArrayList<Exercise> testiName = db.getExercisesByName("testi2");
        Log.d("m", testiName.get(0).getName());


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

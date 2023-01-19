package com.example.trainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.IexerciseDao;

import java.util.ArrayList;

public class ExerciseListActivity extends Activity {


    private IexerciseDao db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseHelper.getInstance(this);
        String[] lista = db.getAllExercises();
        setContentView(R.layout.activity_exercise);

        Button addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(view -> {
            startActivity(new Intent(ExerciseListActivity.this, NewExerciseActivity.class));
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

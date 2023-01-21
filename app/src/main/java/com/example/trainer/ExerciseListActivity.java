package com.example.trainer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {
    private ExerciseDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciselist);
        db = new ExerciseDAO(this);

        handleExercisesToDisplay();

        findViewById(R.id.tempTestBtn).setOnClickListener(view -> {
            Exercise test = db.getExerciseById(845);
            System.out.println("ID QUERY TEST " + test);
        });

//        lv.setOnItemClickListener((adapterView, view, i, l) -> {
//            Log.d("tag","onclick");
//            //put here next activity
//        });

        Button addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(view -> {
            startActivity(new Intent(ExerciseListActivity.this, NewExerciseActivity.class));
        });
    }

    private void handleExercisesToDisplay() {
        ArrayList<Exercise> listOfExercises = db.getAllExercises();
        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (Exercise exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getExerciseName());
        }

        System.out.println(listOfExercises.get(0));

        ListView lv = findViewById(R.id.exerciseList);
        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));
    }

}

package com.example.trainer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
        });
        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            //Log.d("tag","onclick");
            ArrayList<Exercise> exercises = db.getAllExercises();
            Exercise exercise = exercises.get(i);
            String exerciseName = exercise.getExerciseName();
            Toast.makeText(ExerciseListActivity.this, "Exercise "+exerciseName+" selected", Toast.LENGTH_LONG).show();
            System.out.println(exerciseName);
            Intent intent = new Intent(this, CurrentWorkout.class);
            intent.putExtra("name", exerciseName);
            startActivity(intent);
           //put here next activity
       });

        Button addExercise = findViewById(R.id.addExercise);
        addExercise.setOnClickListener(view -> {
            startActivity(new Intent(ExerciseListActivity.this, NewExerciseActivity.class));
        });
    }

    private void handleExercisesToDisplay() {
        ArrayList<Exercise> listOfExercises = db.getAllExercises();
        if (listOfExercises.size() <= 0) return;

        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (Exercise exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getExerciseName());
        }

        ListView lv = findViewById(R.id.exerciseList);
        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));

    }

}

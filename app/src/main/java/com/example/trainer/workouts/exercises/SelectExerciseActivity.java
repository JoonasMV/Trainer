package com.example.trainer.workouts.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.workouts.currentWorkout.CurrentWorkout;

import java.util.ArrayList;

public class SelectExerciseActivity extends AppCompatActivity {

    ExerciseDAO exerciseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exerciseDAO = new ExerciseDAO(this);
        setContentView(R.layout.activity_select_exercise);


        handleExercisesToDisplay();
        ListView lv = findViewById(R.id.lista);



        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("tag","onclick");
            ArrayList<Exercise> exercises = exerciseDAO.getAllExercises();
            Exercise exercise = exercises.get(i);
            String exerciseName = exercise.getExerciseName();
            Toast.makeText(this, "Exercise "+exerciseName+" selected", Toast.LENGTH_LONG).show();
            System.out.println(exerciseName);
            Intent intent = new Intent(this, CurrentWorkout.class);
            intent.putExtra("name", exerciseName);
            startActivity(intent);
        });
    }
    private void handleExercisesToDisplay() {
        ArrayList<Exercise> listOfExercises = exerciseDAO.getAllExercises();
        if (listOfExercises.size() <= 0) return;

        ArrayList<String> exercisesToDisplay = new ArrayList<>();
        for (Exercise exercise: listOfExercises) {
            exercisesToDisplay.add(exercise.getExerciseName());
        }

        ListView lv = findViewById(R.id.lista);
        lv.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercisesToDisplay
        ));
    }
}
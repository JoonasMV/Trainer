package com.example.trainer.workouts.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.MainActivity;
import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.Exercise;

public class NewExerciseActivity extends AppCompatActivity {
    private final ExerciseDAO exerciseDAO = new ExerciseDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        Button newExerciseBtn = findViewById(R.id.newExerciseBtn);
        Button button = findViewById(R.id.button);

        newExerciseBtn.setOnClickListener(view -> {
            saveExercise();
            toExercises();
        });

        button.setOnClickListener(view -> {
            toExercises();
        });
        }


    public void toExercises(){
        Intent i = new Intent(this, MainActivity.class);
        //i.putExtra("FromNewExActivity", true);
        startActivity(i);
    }

    public void saveExercise() {
        TextView tv = findViewById(R.id.exerciseNameInput);
        Exercise exercise = new Exercise(tv.getText().toString());
        exerciseDAO.addExercise(exercise);

    }
}
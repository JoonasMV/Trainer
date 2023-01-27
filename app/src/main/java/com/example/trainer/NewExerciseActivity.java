package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.dao.ExerciseDAO;

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
        Intent i = new Intent(this, ExerciseListActivity.class);
        startActivity(i);
    }

    public void saveExercise() {
        TextView tv = findViewById(R.id.exerciseNameInput);
        String name = tv.getText().toString();
        if (exerciseDAO.addExercise(name) == false) {
            Toast.makeText(NewExerciseActivity.this, "This exercise is already added to the database", Toast.LENGTH_LONG).show();
        };
    }


}
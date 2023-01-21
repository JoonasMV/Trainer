package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainer.database.dao.ExerciseDAO;

public class NewExerciseActivity extends AppCompatActivity {
    private final ExerciseDAO exerciseDAO = new ExerciseDAO(this);

    //please älkää koskeko

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        Button newExerciseBtn = findViewById(R.id.newExerciseBtn);
        newExerciseBtn.setOnClickListener(view -> {
            saveExercise();
            startActivity(new Intent(NewExerciseActivity.this, ExerciseListActivity.class));
        });
        }


    public void toExercises(View v){
        Intent i = new Intent(this, ExerciseListActivity.class);
        startActivity(i);
    }

    public void saveExercise() {
        // tietokantaa oltiin muokattu sen verran että en tiiä oikeen mitä haluttiin mutta tein
        // nyt näin
        TextView tv = findViewById(R.id.exerciseNameInput);
        //TextView tv2 = findViewById(R.id.editTextNumberDecimal);
        //TextView tv3 = findViewById(R.id.editTextNumber);
        String name = tv.getText().toString();
        //double weight = Double.parseDouble(tv2.getText().toString());
        //int sets = Integer.parseInt(tv3.getText().toString());
        exerciseDAO.addExercise(name);
    }


}
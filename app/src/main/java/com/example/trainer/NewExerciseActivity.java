package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
    }

    public void toExercises(View v){
        Intent i = new Intent(this, ExerciseListActivity.class);
        startActivity(i);
    }

    public void addExercise(View v){
        TextView tv = findViewById(R.id.editTextTextPersonName2);
        String name = tv.getText().toString();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }
}
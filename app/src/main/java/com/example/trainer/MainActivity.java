package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button exercisesBtn;
    Button workoutsBtn;
    Button progressBtn;
    TextView userGreetText;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        userGreetText = findViewById(R.id.userGreetText);
        userGreetText.setText("Welcome back " + username);

        if (username == null || username.length() == 0) {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        }

        exercisesBtn = findViewById(R.id.exercisesBtn);
        workoutsBtn = findViewById(R.id.workoutsBtn);
        progressBtn = findViewById(R.id.progressBtn);

        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

                boolean b = dbHelper.addOne();
                Toast.makeText(MainActivity.this, "Success= " + b, Toast.LENGTH_SHORT).show();
            }
        });


        exercisesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExerciseListActivity.class));
            }
        });

        workoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });


    }
}
package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.UserHelper;

public class MainActivity extends AppCompatActivity {
    String username = "";
    UserHelper db = new UserHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exercisesBtn = findViewById(R.id.exercisesBtn);
        Button workoutsBtn = findViewById(R.id.workoutsBtn);
        Button progressBtn = findViewById(R.id.progressBtn);

        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Success= " + username, Toast.LENGTH_SHORT).show();
            }
        });
        exercisesBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ExerciseListActivity.class)));
        workoutsBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));
        progressBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        username = db.getUser();
        //username = getIntent().getStringExtra("username");
        TextView userGreetText = findViewById(R.id.userGreetText);
        userGreetText.setText("Welcome back " + username);

        if (username == null || username.length() == 0) {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        }
    }

}
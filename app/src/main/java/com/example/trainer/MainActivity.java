package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.dao.UserDAO;
import com.example.trainer.database.schemas.User;

public class MainActivity extends AppCompatActivity {
    UserDAO userDAO;// = new UserDAO(MainActivity.this);
    TextView userGreetText;
    String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDAO = new UserDAO(this);
        handleUserLogin();

        userGreetText = findViewById(R.id.userGreetText);
        userGreetText.setText("Welcome back " + username);

        if (username == null || username.length() == 0) {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        }

        Button exercisesBtn = findViewById(R.id.exercisesBtn);
        Button workoutsBtn = findViewById(R.id.workoutsBtn);
        Button progressBtn = findViewById(R.id.progressBtn);

        exercisesBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ExerciseListActivity.class)));

        workoutsBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, WorkoutSelectionActivity.class)));

        progressBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, WorkoutSelectionActivity.class)));

    }

    private void handleUserLogin() {
        User user = userDAO.getUser();
        if (user != null) username = user.getUsername();

        if (username == null) {
            username = getIntent().getStringExtra("username");
            User newUser = new User(username);
            try {
                userDAO.createUser(newUser);
                Toast.makeText(MainActivity.this, "Username added", Toast.LENGTH_LONG);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error creating username", Toast.LENGTH_LONG).show();
                System.out.println("Exception in handleUserLogin " + e.getMessage());
            }
        }
    }
}
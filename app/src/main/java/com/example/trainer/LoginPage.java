package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.trainer.database.UserDAO;
//import android.widget.Toast;


public class LoginPage extends AppCompatActivity {
    EditText nameInput;
    Button startBtn;
    UserDAO userDAO = new UserDAO(LoginPage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        nameInput = findViewById(R.id.nameInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            intent.putExtra("username", username);
            userDAO.createUser(username);
            startActivity(intent);
        });
    }

}
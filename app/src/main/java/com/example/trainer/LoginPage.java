package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.trainer.database.dao.UserDAO;
import com.example.trainer.database.schemas.User;
import com.example.trainer.mainActivity.MainActivity;
//import android.widget.Toast;


public class LoginPage extends AppCompatActivity {
    EditText nameInput;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        nameInput = findViewById(R.id.nameInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            new UserDAO().createUser(new User(username));
            startActivity(new Intent(this, MainActivity.class));
        });
    }

}
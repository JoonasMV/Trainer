package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;

import com.example.trainer.database.DatabaseHelper;

public class LoginPage extends AppCompatActivity {
    EditText nameInput;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
//        DatabaseHelper DbHelper = new DatabaseHelper(LoginPage.this);

        nameInput = findViewById(R.id.nameInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            intent.putExtra("username", username);
//            boolean t = DbHelper.insertUserName(username);
//            DbHelper.close();
//            Toast.makeText(LoginPage.this, "success = "+ t, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

}
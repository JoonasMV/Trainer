package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
            Bundle bundle = new Bundle();
            bundle.putString("username", username);

            WelcomeScreen_fragment fragObj = new WelcomeScreen_fragment();
            fragObj.setArguments(bundle);
        });
    }

}
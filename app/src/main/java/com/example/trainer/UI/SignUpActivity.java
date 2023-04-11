package com.example.trainer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText nameInput;
    EditText passwordInput;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);


        nameInput = findViewById(R.id.signUpNameInput);
        passwordInput = findViewById(R.id.signUpPasswordInput);
        signupBtn = findViewById(R.id.signUpButton);

        signupBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            String password = passwordInput.getText().toString();
            User user = new User(username, password);

            TrainerController controller = BaseController.getController();
            controller.registerUser(user);

            startActivity(new Intent(this, MainActivity.class));
        });
    }



}

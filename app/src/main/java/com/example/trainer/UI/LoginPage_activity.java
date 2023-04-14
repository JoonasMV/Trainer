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
//import android.widget.Toast;


public class LoginPage_activity extends AppCompatActivity {
    EditText nameInput;
    EditText passwordInput;
    Button startBtn;

    Button SignUpInsteadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);


        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);
        startBtn = findViewById(R.id.signUpButton);
        SignUpInsteadBtn = findViewById(R.id.registerInstead);

        SignUpInsteadBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            String password = passwordInput.getText().toString();
            User user = new User(username, password);

            TrainerController controller = BaseController.getController();
            controller.authenticateUser(user);

            startActivity(new Intent(this, MainActivity.class));
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
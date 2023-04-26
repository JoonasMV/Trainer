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
import com.example.trainer.util.Toaster;

import java.util.concurrent.Future;

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
            registerUserOnBackground(user);
            //finish();
        });
    }

    private void registerUserOnBackground(User user){
        TrainerController controller = BaseController.getController();
        new Thread(() -> {
            Future<Boolean> result = controller.registerUserAsync(user);
            try {
                if(result.get()){
                    runOnUiThread(() -> {
                        startActivity(new Intent(this, MainActivity.class));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, LoginPage_activity.class));
        finish();
    }



}

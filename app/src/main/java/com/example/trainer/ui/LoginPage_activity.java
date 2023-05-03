package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.R;
import com.example.trainer.diagram.api.controllers.BaseController;
import com.example.trainer.diagram.api.controllers.TrainerController;
import com.example.trainer.diagram.api.model.User;

import java.util.concurrent.Future;
//import android.widget.Toast;

/**
    Activity for logging in the app
 */
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
            authenticateOnBackground(user);
        });
    }

    /**
     * Authenticates the user in the background, if the user is found, opens the MainActivity
     * @param user  the user that is trying to log in
     */
    private void authenticateOnBackground(User user){
        TrainerController controller = BaseController.getController();
        new Thread(() -> {
            Future<Boolean> result = controller.authenticateUserAsync(user);
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
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
import com.example.trainer.serverConnector.Server;
//import android.widget.Toast;


public class LoginPage_activity extends AppCompatActivity {
    EditText nameInput;
    EditText passwordInput;
    Button startBtn;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);

        server = Server.getInstance();

        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            String password = passwordInput.getText().toString();
            User user = new User(username, password);

            TrainerController controller = BaseController.getController();
            controller.registerUser(user);

//            for (String exerciseName: AppInitiation.basicExercises) {
//                ExerciseType exerciseType = server.exerciseType().getByName(exerciseName);
//                exerciseTypeDAO.save(exerciseType);
//            }

            startActivity(new Intent(this, MainActivity.class));
        });
    }

}
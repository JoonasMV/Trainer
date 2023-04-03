package com.example.trainer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.serverConnector.Server;
import com.example.trainer.util.AppInitiation;
//import android.widget.Toast;


public class LoginPage_activity extends AppCompatActivity {
    EditText nameInput;
    Button startBtn;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);

        server = Server.getInstance();

        nameInput = findViewById(R.id.nameInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            BaseController.getController().createUser(new User(username));
//            for (String exerciseName: AppInitiation.basicExercises) {
//                ExerciseType exerciseType = server.exerciseType().getByName(exerciseName);
//                exerciseTypeDAO.save(exerciseType);
//            }

            startActivity(new Intent(this, MainActivity.class));
        });
    }

}
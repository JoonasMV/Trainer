package com.example.trainer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.sqlite.BetterSqliteDAOFactory;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.serverConnector.Server;
import com.example.trainer.util.AppInitiation;
//import android.widget.Toast;


public class LoginPage_activity extends AppCompatActivity {
    EditText nameInput;
    Button startBtn;
    IExerciseTypeDAO exerciseTypeDAO;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);

        exerciseTypeDAO = new BetterSqliteDAOFactory().createExerciseTypeDAO();
        server = Server.getInstance();

        nameInput = findViewById(R.id.nameInput);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(view -> {
            String username = nameInput.getText().toString();
            BaseController.getController().createUser(new User(username));
            for (String exerciseName: AppInitiation.basicExercises) {
                ExerciseType exerciseType = server.exerciseType().getByName(exerciseName);
                exerciseTypeDAO.save(exerciseType);
            }

            startActivity(new Intent(this, MainActivity.class));
        });
    }

}
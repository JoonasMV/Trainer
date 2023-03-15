package com.example.trainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.dao.UserDAO;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.database.schemas.User;
import com.example.trainer.exercises.ExerciseChart;
import com.example.trainer.util.Toaster;

public class WelcomeScreen_fragment extends Fragment {
    private UserDAO userDAO;

    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        userDAO = new UserDAO();
        TextView userGreetText = getView().findViewById(R.id.userGreetText);
        getUserFromDb();

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));

        view.findViewById(R.id.testing).setOnClickListener(v -> {
         getParentFragmentManager().beginTransaction()
                 .replace(R.id.mainContainer, new ExerciseChart())
                 .addToBackStack(null).commit();

        });
    }

    private void getUserFromDb(){
        this.user = userDAO.getUser();
    }

}
package com.example.trainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainer.database.dao.UserDAO;
import com.example.trainer.database.schemas.User;

public class WelcomeScreen_fragment extends Fragment {
    UserDAO userDAO;
    String username = "Temp value";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        userDAO = new UserDAO(this.getContext());
        TextView userGreetText = getView().findViewById(R.id.userGreetText);
        handleUserLogin();

        if (username == null || username.length() == 0) {
            startActivity(new Intent(this.getContext(), LoginPage.class));
        }
        userGreetText.setText("Welcome back " + username);
    }


    private void handleUserLogin() {
        User user = userDAO.getUser();
        if (user != null) username = user.getUsername();
    }
}
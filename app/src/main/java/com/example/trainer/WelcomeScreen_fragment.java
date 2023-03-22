package com.example.trainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.schemas.User;
import com.example.trainer.controllers.WorkoutManager;

public class WelcomeScreen_fragment extends Fragment {

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
        TextView userGreetText = getView().findViewById(R.id.userGreetText);
        user = WorkoutManager.getInstance().findUser();

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));
    }


}
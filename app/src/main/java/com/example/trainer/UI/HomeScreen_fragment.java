package com.example.trainer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainer.R;
import com.example.trainer.UI.users.userSearch.User_search_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.User;

public class HomeScreen_fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView userGreetText = requireView().findViewById(R.id.userGreetText);
        User user = BaseController.getController().findUser();

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage_activity.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));

        view.findViewById(R.id.usersBtn).setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.mainContainer, new User_search_fragment())
                    .commit();
        });
    }


}
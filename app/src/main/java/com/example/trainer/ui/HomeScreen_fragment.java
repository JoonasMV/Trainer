package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.User;
import com.example.trainer.ui.users.userSearch.User_search_fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        TextView quoteOfTheDay = view.findViewById(R.id.quoteOfTheDay);

        TrainerController controller = BaseController.getController();
        User user = controller.findUser();
        List<String> quotes = controller.getQuotes();
        int quoteIndex = new Random().nextInt(quotes.size());

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage_activity.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));
        //TODO: remove comments after quotes have been added
        //quoteOfTheDay.setText(quotes.get(quoteIndex));
    }


}
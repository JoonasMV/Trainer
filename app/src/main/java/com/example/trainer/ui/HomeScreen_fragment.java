package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.User;
import com.example.trainer.ui.users.userSearch.UserSearchAdapter;
import com.example.trainer.ui.users.userSearch.User_search_fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Trainer app's home screen, opens when the user has logged in
 */
public class HomeScreen_fragment extends Fragment {

    private List<String> quotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleQuoteFetching();
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

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage_activity.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));
        //TODO: remove comments after quotes have been added
        if (quotes != null) {
            int quoteIndex = new Random().nextInt(quotes.size());
            quoteOfTheDay.setText(quotes.get(quoteIndex));
        }
    }

    /**
     * Fetches the quotes from the database in the background
     */
    private void handleQuoteFetching(){
        new Thread(() -> {
            quotes = BaseController.getController().getQuotes();
        }).start();

    }
}
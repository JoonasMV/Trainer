package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HomeScreen_fragment extends Fragment {

    private String quote;

    private TextView quoteOfTheDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.quote == null){
            handleQuoteFetching();
        }else {
            quote = MainActivity.quote;
        }
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
        quoteOfTheDay = view.findViewById(R.id.quoteOfTheDay);


        TrainerController controller = BaseController.getController();
        User user = controller.findUser();

        if(user == null){
            startActivity(new Intent(this.getContext(), LoginPage_activity.class));
            return;
        }
        userGreetText.setText(String.format("Welcome back %s", user.getUsername()));
        //TODO: remove comments after quotes have been added

        if(quote != null){
            quoteOfTheDay.setText(quote);
        }
    }

    public void updateQuote(String dailyQuote){
        if(quoteOfTheDay != null){
            quoteOfTheDay.setText(dailyQuote);
            MainActivity.quote = dailyQuote;
        }
    }

    /**
     * Fetches the quotes from the database in the background
     */

    private void handleQuoteFetching(){
        new Thread(() -> {

            Future<String> result = BaseController.getController().getQuotes();
            try {
                String dailyQuote = result.get();
                FragmentActivity activity = getActivity();
                if (activity == null) return;
                activity.runOnUiThread(() -> {
                    updateQuote(dailyQuote);
                });
            } catch (InterruptedException | ExecutionException e) {
                Toast.makeText(getContext(), "Failed to load quote", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

}
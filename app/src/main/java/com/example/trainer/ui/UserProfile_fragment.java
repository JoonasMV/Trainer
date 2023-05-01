package com.example.trainer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;
import com.example.trainer.util.Toaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Public profile for the app's users
 */
public class UserProfile_fragment extends Fragment {

    private TextView userName;

    private RecyclerView workoutList;

    private ProgressBar bar;

    private String username;

    private final static String USERNAME_KEY = "username";


    public UserProfile_fragment() {
        // Required empty public constructor
    }


    public static UserProfile_fragment newInstance(String username) {
        UserProfile_fragment fragment = new UserProfile_fragment();
        Bundle args = new Bundle();
        args.putString(USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        userName = view.findViewById(R.id.userName);
        workoutList = view.findViewById(R.id.userWorkouts);
        bar = view.findViewById(R.id.profileProgressBar);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String username = getArguments().getString(USERNAME_KEY);
        Objects.requireNonNull(username);
        userName.setText(username);

        List<Workout> list = new ArrayList<>();

        UserProfileAdapter adapter = new UserProfileAdapter(list, getContext());

        workoutList.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutList.setAdapter(adapter);
        handleWorkoutFetching(adapter, username);
    }

    /**
     * Fetches the user's shared workouts
     * @param adapter   the adapter the data is given to
     * @param username  the user's username
     */

    private void handleWorkoutFetching(UserProfileAdapter adapter, String username){
        bar.setProgress(0);
        new Thread(() -> {
            Future<List<Workout>> result = BaseController.getController().getSharedWorkoutsAsync(username);
            try {
                List<Workout> workouts = result.get();
                FragmentActivity activity = getActivity();
                if (activity == null) return;
                activity.runOnUiThread(() -> {
                    adapter.update(workouts);
                    bar.setVisibility(View.GONE);

                });
            } catch (InterruptedException | ExecutionException e) {
                Toaster.toast(getContext(), "Failed to load workouts");
            }
        }).start();
    }
}
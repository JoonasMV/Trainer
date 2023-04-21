package com.example.trainer.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.UI.exercises.exerciseChart.ExerciseChartAdapter;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserProfile_fragment extends Fragment {

    private TextView userName;

    private RecyclerView workoutList;

    private User user;


    public UserProfile_fragment() {
        // Required empty public constructor
    }


    public static UserProfile_fragment newInstance(User user) {
        UserProfile_fragment fragment = new UserProfile_fragment();
        Bundle args = new Bundle();
        args.putSerializable(null, user);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName.setText("Nimi");

        List<Workout> list = new ArrayList<>();
        //placeholders
        //TODO: real values
        list.add(new Workout("workout", new Date(), new Date()));
        list.add(new Workout("workout2", new Date(), new Date()));
        list.add(new Workout("workout3", new Date(), new Date()));

        UserProfileAdapter adapter = new UserProfileAdapter(list, getContext());
        workoutList.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutList.setAdapter(adapter);
    }
}
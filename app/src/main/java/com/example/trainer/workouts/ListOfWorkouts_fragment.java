package com.example.trainer.workouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trainer.MainActivity;
import com.example.trainer.R;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;


public class ListOfWorkouts_fragment extends Fragment {

    private Button newWorkoutBtn;

    public ListOfWorkouts_fragment() {
        // Required empty public constructor
    }


    public static ListOfWorkouts_fragment newInstance(String param1, String param2) {
        ListOfWorkouts_fragment fragment = new ListOfWorkouts_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_workouts_fragment, container, false);

        newWorkoutBtn = view.findViewById(R.id.newWorkoutBtn);

        newWorkoutBtn.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, new CurrentWorkoutFragment()).commit();
        });

        return view;
    }



    }

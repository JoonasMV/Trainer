package com.example.trainer.workouts.currentWorkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;
import com.example.trainer.workouts.ListOfWorkouts_fragment;
import com.example.trainer.workouts.exercises.selectExercise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CurrentWorkoutFragment extends Fragment {

    private Button cancelWorkoutBtn;

    private Button addExerciseBtn;

    public CurrentWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        cancelWorkoutBtn = v.findViewById(R.id.cancelWorkoutBtn);
        cancelWorkoutBtn.setOnClickListener(view -> {
            System.out.println(getParentFragmentManager());
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer,ListOfWorkouts_fragment.class, null).commit();
        });

        addExerciseBtn = v.findViewById(R.id.addExerciseBtn);
        addExerciseBtn.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContainer, selectExercise.class, null).commit();
        });
        //TODO: test items
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        List setlist = new ArrayList<>();
        List setlist2 = new ArrayList<>();
        setlist.add(new ExerciseSet(100, 3));
        setlist.add(new ExerciseSet(105, 2));
        setlist2.add(new ExerciseSet(50, 10));
        setlist2.add(new ExerciseSet(25, 20));
        Exercise testEx1 = new Exercise("squat");
        Exercise testEx2 = new Exercise("bench");
        testEx1.setSetList(setlist);
        testEx2.setSetList(setlist2);
        exerciseList.add(testEx1);
        exerciseList.add(testEx2);

        Workout testWorkout = new Workout(
                "test workout",
                new Date(),
                new Date()
        );
        testWorkout.setExList(exerciseList);
        //------------------
        TextView workoutName = v.findViewById(R.id.workoutName);
        workoutName.setText(testWorkout.getName());

        // Recycler view initiation
        RecyclerView listOfWorkouts = v.findViewById(R.id.listOfExercises);
        listOfWorkouts.setAdapter(new ExerciseAdapter(testWorkout, getContext()));
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
}
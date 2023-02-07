package com.example.trainer.workouts.currentWorkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trainer.MainActivity;
import com.example.trainer.R;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentWorkout extends AppCompatActivity {
    private Button cancelWorkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_view);

        cancelWorkoutBtn = findViewById(R.id.cancelWorkoutBtn);
        cancelWorkoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        //TODO: test items
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        List setlist = new ArrayList<>();
        List setlist2 = new ArrayList<>();
        setlist.add(new ExerciseSet(100, 3));
        setlist.add(new ExerciseSet(105,2));
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
        TextView workoutName = findViewById(R.id.workoutName);
        workoutName.setText(testWorkout.getName());

        // Recycler view initation
        RecyclerView listOfWorkouts = findViewById(R.id.listOfExercises);
        listOfWorkouts.setAdapter(new ExerciseAdapter(testWorkout, this));
        listOfWorkouts.setLayoutManager(new LinearLayoutManager(this));
    }
}
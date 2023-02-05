package com.example.trainer.workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.ExerciseSet;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_view);

        //TODO: test items
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        List setlist = new ArrayList<>();
        setlist.add(new ExerciseSet(10, 3));
        setlist.add(new ExerciseSet(10,2));
        Exercise testEx1 = new Exercise("squat");
        Exercise testEx2 = new Exercise("bench");
        testEx1.setSetList(setlist);
        testEx2.setSetList(setlist);
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
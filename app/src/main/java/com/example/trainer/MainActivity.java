package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.workouts.exercises.ListOfExercises_fragment;
import com.example.trainer.workouts.ListOfWorkouts_fragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.mainContainer, WelcomeScreen_fragment.class, savedInstanceState)
                .addToBackStack(null)
                .commit();
        }

        Intent i = getIntent();
        if (i.hasExtra("FromNewExActivity") && i.getExtras().getBoolean("FromNewExActivity")) {
            fragmentHandler(new ListOfExercises_fragment());
        }

        setContentView(R.layout.activity_main);

        Button exercisesBtn = findViewById(R.id.exercisesBtn);
        Button workoutsBtn = findViewById(R.id.workoutsBtn);
        Button progressBtn = findViewById(R.id.progressBtn);
        Button homeBtn = findViewById(R.id.homeBtn);

        exercisesBtn.setOnClickListener(view -> fragmentHandler(new ListOfExercises_fragment()));
        homeBtn.setOnClickListener(view -> fragmentHandler(new WelcomeScreen_fragment()));
        workoutsBtn.setOnClickListener(view -> fragmentHandler(ListOfWorkouts_fragment.newInstance(null, null)));

        progressBtn.setOnClickListener(view -> fragmentHandler(new CurrentWorkoutFragment()));

        WorkoutDAO dao = new WorkoutDAO(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void fragmentHandler(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment.getClass(), null)
                .addToBackStack(null)
                .commit();
    }
}

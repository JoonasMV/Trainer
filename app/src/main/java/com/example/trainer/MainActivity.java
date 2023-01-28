package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.trainer.workouts.CurrentWorkout;
import com.example.trainer.exercises.ListOfExercises_fragment;
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
                .add(R.id.fragmentContainerView2, WelcomeScreen_fragment.class, null)
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
        workoutsBtn.setOnClickListener(view -> fragmentHandler(new ListOfWorkouts_fragment()));

        progressBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CurrentWorkout.class)));
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
                .replace(R.id.fragmentContainerView2, fragment.getClass(), null)
                .addToBackStack(null)
                .commit();
    }
}

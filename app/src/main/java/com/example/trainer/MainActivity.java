package com.example.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.workouts.currentWorkout.CurrentWorkoutFragment;
import com.example.trainer.exercises.ListOfExercises_fragment;
import com.example.trainer.workouts.ListOfWorkouts_fragment;
import com.example.trainer.workouts.currentWorkout.WorkoutViewModel;
import com.example.trainer.workouts.workoutHistory.WorkoutHistory_fragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.initialize(this);

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            createFragmentManager();
        }

        findViewById(R.id.exercisesBtn).setOnClickListener(view -> fragmentHandler(new ListOfExercises_fragment()));
        findViewById(R.id.homeBtn).setOnClickListener(view -> fragmentHandler(new WelcomeScreen_fragment()));
        findViewById(R.id.workoutsBtn).setOnClickListener(view -> fragmentHandler(new ListOfWorkouts_fragment()));
        findViewById(R.id.progressBtn).setOnClickListener(view -> fragmentHandler(new WorkoutHistory_fragment()));

        WorkoutDAO dao = new WorkoutDAO();
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

    private void createFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainContainer, WelcomeScreen_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}

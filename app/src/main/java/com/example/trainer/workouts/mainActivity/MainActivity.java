package com.example.trainer.workouts.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.trainer.R;
import com.example.trainer.WelcomeScreen_fragment;
import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.WorkoutDAO;
import com.example.trainer.exercises.ListOfExercises_fragment;
import com.example.trainer.workouts.ListOfPresetWorkouts_fragment;
import com.example.trainer.workouts.currentWorkout.WorkoutManager;
import com.example.trainer.workouts.workoutHistory.WorkoutHistory_fragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.initialize(this);

        setContentView(R.layout.activity_main);

        if(fragmentManager == null){
            createFragmentManager();
        }

        findViewById(R.id.exercisesBtn).setOnClickListener(view -> fragmentHandler(new ListOfExercises_fragment()));
        findViewById(R.id.homeBtn).setOnClickListener(view -> fragmentHandler(new WelcomeScreen_fragment()));
        findViewById(R.id.workoutsBtn).setOnClickListener(view -> fragmentHandler(new ListOfPresetWorkouts_fragment()));
        findViewById(R.id.progressBtn).setOnClickListener(view -> fragmentHandler(new WorkoutHistory_fragment()));

        WorkoutDAO dao = new WorkoutDAO();

        WorkoutManager.getInstance().readFromPref(getApplicationContext());
    }


    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    private void fragmentHandler(Fragment fragment) {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainContainer);
        if(currentFragment.getClass().equals(fragment.getClass())) return;

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

    @Override
    public void onStop() {
        super.onStop();
        WorkoutManager.getInstance().saveToPref(getApplicationContext());
    }
}

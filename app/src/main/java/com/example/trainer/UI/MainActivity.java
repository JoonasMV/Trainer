package com.example.trainer.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.serverConnector.Server;
import com.example.trainer.UI.exercises.exerciseList.ExerciseList_fragment;
import com.example.trainer.UI.workouts.PresetWorkouts_fragment;
import com.example.trainer.UI.workouts.workoutHistory.WorkoutHistory_fragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkoutController.initController(this);

        setContentView(R.layout.main_activity);

        if(fragmentManager == null){
            createFragmentManager();
        }

        findViewById(R.id.exercisesBtn).setOnClickListener(view -> fragmentHandler(new ExerciseList_fragment()));
        findViewById(R.id.homeBtn).setOnClickListener(view -> fragmentHandler(new HomeScreen_fragment()));
        findViewById(R.id.workoutsBtn).setOnClickListener(view -> fragmentHandler(new PresetWorkouts_fragment()));
        findViewById(R.id.progressBtn).setOnClickListener(view -> fragmentHandler(new WorkoutHistory_fragment()));

        Server server = Server.getInstance();

        BaseController.getController().readFromPref(getApplicationContext());
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

        assert currentFragment != null;
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
                .replace(R.id.mainContainer, HomeScreen_fragment.class, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseController.getController().saveToPref(getApplicationContext());
    }
}

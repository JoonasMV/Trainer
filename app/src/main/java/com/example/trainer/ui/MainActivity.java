package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.trainer.R;
import com.example.trainer.ui.exercises.exerciseList.ExerciseList_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.ui.users.userSearch.User_search_fragment;
import com.example.trainer.ui.workouts.workoutHistory.WorkoutHistory_fragment;
import com.example.trainer.ui.workouts.presetWorkouts.PresetWorkouts_fragment;
import com.example.trainer.ui.workouts.workoutStats.WorkoutStats_fragment;
import com.example.trainer.util.Toaster;

/**
 * Trainer App's main activity
 */

public class MainActivity extends AppCompatActivity {

    public static String quote;
    private FragmentManager fragmentManager;
    private TrainerController controller;

    private PopupMenu ppMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkoutController.initController(this);
        controller = BaseController.getController();
        setContentView(R.layout.main_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View v =getSupportActionBar().getCustomView();


        if(controller.sessionValid()){
            controller.refreshSession();
            controller.fetchWorkoutsAndExerciseTypesOnBackground();
        }

        if(fragmentManager == null){
            createFragmentManager();
        }

        ImageView threeDots = v.findViewById(R.id.moreImage);


        threeDots.setOnClickListener(view -> {
            ppMenu = new PopupMenu(v.getContext(), threeDots );
            ppMenu.getMenuInflater().inflate(R.menu.toolbar_popup_menu, ppMenu.getMenu());
            ppMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.logOut:
                        controller.logOut();
                        startActivity(new Intent(getApplicationContext(), LoginPage_activity.class));
                        break;
                    case R.id.otherUsers:
                        fragmentManager
                                .beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.mainContainer, new User_search_fragment())
                                .commit();
                        break;
                }
                return true;
            });
            ppMenu.show();
        });
        findViewById(R.id.exercisesBtn).setOnClickListener(view -> fragmentHandler(new ExerciseList_fragment()));
        findViewById(R.id.homeBtn).setOnClickListener(view -> fragmentHandler(new HomeScreen_fragment()));
        findViewById(R.id.workoutsBtn).setOnClickListener(view -> fragmentHandler(new PresetWorkouts_fragment()));
        findViewById(R.id.progressBtn).setOnClickListener(view -> fragmentHandler(new WorkoutHistory_fragment()));
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

    /**
     * Handles the changing of fragments inside the main activity's fragment container
     * @param fragment  the fragment that is changed to the mainContainer
     */
    private void fragmentHandler(Fragment fragment) {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainContainer);

        if(!controller.sessionValid()){
            startActivity(new Intent(this, LoginPage_activity.class));
        }

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

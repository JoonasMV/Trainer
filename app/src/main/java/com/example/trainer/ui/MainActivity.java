package com.example.trainer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.trainer.R;
import com.example.trainer.ui.exercises.exerciseList.ExerciseList_fragment;
import com.example.trainer.diagram.api.controllers.BaseController;
import com.example.trainer.diagram.api.controllers.TrainerController;
import com.example.trainer.diagram.api.controllers.WorkoutController;
import com.example.trainer.ui.users.userSearch.User_search_fragment;
import com.example.trainer.ui.workouts.workoutHistory.WorkoutHistory_fragment;
import com.example.trainer.ui.workouts.presetWorkouts.PresetWorkouts_fragment;
import com.example.trainer.diagram.api.util.Toaster;

/**
 * Trainer App's main activity
 */

public class MainActivity extends AppCompatActivity {

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

        String[] menuButtonStrings = new String[4];
        menuButtonStrings[0] = getString(R.string.exercises);
        menuButtonStrings[1] = getString(R.string.home);
        menuButtonStrings[2] = getString(R.string.workouts);
        menuButtonStrings[3] = getString(R.string.history);

        for(int i = 0; i < menuButtonStrings.length; i++){
            if(menuButtonStrings[i].length() > 9){
                menuButtonStrings[i] = menuButtonStrings[i].substring(0, 5) + "...";
            }
        }


        //exercisebutton
        Button exerciseBtn = findViewById(R.id.exercisesBtn);
        exerciseBtn.setText(menuButtonStrings[0]);
        exerciseBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showTooltip(getString(R.string.exercises));
                return true;
            }
        });

        exerciseBtn.setOnClickListener(view -> fragmentHandler(new ExerciseList_fragment()));


        //homebutton
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setText(menuButtonStrings[1]);
        homeBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showTooltip(getString(R.string.home));
                return true;
            }
        });
        homeBtn.setOnClickListener(view -> fragmentHandler(new HomeScreen_fragment()));


        //workoutsbutton
        Button workoutsBtn = findViewById(R.id.workoutsBtn);
        workoutsBtn.setText(menuButtonStrings[2]);
        workoutsBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showTooltip(getString(R.string.workouts));
                return true;
            }
        });
        workoutsBtn.setOnClickListener(view -> fragmentHandler(new PresetWorkouts_fragment()));


        //progressbutton
        Button progressBtn = findViewById(R.id.progressBtn);
        progressBtn.setText(menuButtonStrings[3]);
        progressBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showTooltip(getString(R.string.history));
                return true;
            }
        });
        progressBtn.setOnClickListener(view -> fragmentHandler(new WorkoutHistory_fragment()));


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


    public void showTooltip(String stringToShow){

        Toaster.longToast(this, stringToShow);
    }
}

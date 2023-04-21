package com.example.trainer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

import static org.mockito.Mockito.*;

import android.widget.Button;
import android.widget.TextView;

import com.example.trainer.UI.MainActivity;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;


import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class LoggedInTest {

    private TrainerController mockController;

    @Before
    public void setup() {
        mockController = mock(TrainerController.class);
        BaseController.setController(mockController);
        when(mockController.sessionValid()).thenReturn(true);
        when(mockController.findUser()).thenReturn(new User("test", "yeet"));
    }


    @Test
    public void whenLoggedInMainActivityLoads(){
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            TextView welcomeText = activity.findViewById(R.id.userGreetText);
            assertThat(welcomeText.getText().toString()).isEqualTo("Welcome back test");
        }
    }

    @Test
    public void navBarIsLoaded() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            Button exerciseBtn = activity.findViewById(R.id.exercisesBtn);
            Button workoutBtn = activity.findViewById(R.id.workoutsBtn);
            Button homeBtn = activity.findViewById(R.id.homeBtn);
            Button progressBtn = activity.findViewById(R.id.progressBtn);

            assertThat(exerciseBtn).isNotNull();
            assertThat(workoutBtn).isNotNull();
            assertThat(homeBtn).isNotNull();
            assertThat(progressBtn).isNotNull();

            assertThat(exerciseBtn.getText().toString()).isEqualTo("Exercises");
            assertThat(workoutBtn.getText().toString()).isEqualTo("Workout");
            assertThat(homeBtn.getText().toString()).isEqualTo("Home");
            assertThat(progressBtn.getText().toString()).isEqualTo("History");
        }
    }

//    @Test
//    public void exerciseTypesAreRendered() {
//        when(mockController.getExerciseTypes()).thenReturn(getMockTypes());
//
//
//
//        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
//            controller.setup();
//            MainActivity activity = controller.get();
//            boolean onClickCalled = activity.findViewById(R.id.exercisesBtn).performClick();
//
//            assertThat(onClickCalled).isTrue();
//
//            verify(mockController, times(1)).getExerciseTypes();
//
//            TextView title = activity.findViewById(R.id.exerciseTitle);
//
//            assertThat(title.getText().toString()).isEqualTo("Exercises");
//
//            TextView userGreetText = activity.findViewById(R.id.userGreetText);
//
//            assertThat(userGreetText).isNull();
//        }
//    }
//
//    private List<ExerciseType> getMockTypes(){
//        List<ExerciseType> types = new ArrayList<>();
//        types.add(new ExerciseType("test"));
//        types.add(new ExerciseType("test2"));
//        types.add(new ExerciseType("test3"));
//        return types;
//    }

}

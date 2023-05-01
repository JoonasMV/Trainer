package com.example.trainer;

import com.example.trainer.ui.LoginPage_activity;
import com.example.trainer.ui.MainActivity;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

import static org.mockito.Mockito.*;

import android.content.Intent;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void activityStarts(){
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            assertThat(activity).isNotNull();
        }
    }

    @Test
    public void loginPageIsVisible() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            Intent expectedIntent = new Intent(activity, LoginPage_activity.class);
            Intent actual = shadowOf(activity).getNextStartedActivity();
            assertThat(expectedIntent.getComponent()).isEqualTo(actual.getComponent());
        }
    }

    @Test
    public void loginWorks() throws InterruptedException {
        TrainerController mockController = mock(WorkoutController.class);
        when(mockController.authenticateUserAsync(any())).thenReturn(new Future<Boolean>() {
            @Override
            public boolean cancel(boolean b) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Boolean get() throws ExecutionException, InterruptedException {
                return true;
            }

            @Override
            public Boolean get(long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }
        });
        BaseController.setController(mockController);
        try (ActivityController<LoginPage_activity> controller = Robolectric.buildActivity(LoginPage_activity.class)) {
            controller.setup();
            LoginPage_activity activity = controller.get();

            EditText nameInput = activity.findViewById(R.id.nameInput);
            EditText passwordInput = activity.findViewById(R.id.passwordInput);

            nameInput.setText("test");
            passwordInput.setText("yeet");
            activity.findViewById(R.id.signUpButton).performClick();
            Thread.sleep(100);
            verify(mockController, times(1)).authenticateUserAsync(isA(User.class));
        }
    }

}

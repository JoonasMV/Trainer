package com.example.trainer;

import com.example.trainer.api.TrainerAPIWrapper;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.mock.MockAPI;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

public class ControllerTest {
    private final MockAPI api = new MockAPI();
    private TrainerController controller = new WorkoutController(api);

    @Before
    public void beforeEach(){
        controller = new WorkoutController(api);
    }

    @Test
    public void startsWorkout() {
        controller.startWorkout("test");
        assertThat(controller.workoutActive()).isTrue();
        Workout workout = controller.getWorkout();
        assertThat(workout.getName()).isEqualTo("test");
    }

    @Test
    public void cancelsWorkout(){
        controller.startWorkout("test");
        assertThat(controller.workoutActive()).isTrue();
        controller.cancelWorkout(null);
        assertThat(controller.workoutActive()).isFalse();
    }

    @Test
    public void savesWorkout(){
        controller.startWorkout("test");
        controller.saveWorkout();
        assertThat(controller.workoutActive()).isFalse();
        assertThat(api.getRecentParam()).isInstanceOf(Workout.class);
        Workout workout = (Workout) api.getRecentParam();
        assertThat(workout.getName()).isEqualTo("test");
    }

    @Test
    public void changesWorkoutName(){
        controller.startWorkout("test");
        controller.changeWorkoutName("new name");
        Workout workout = controller.getWorkout();
        assertThat(workout.getName()).isEqualTo("new name");
    }

    @Test
    public void workoutActiveWorks(){
        controller.startWorkout("test");
        assertThat(controller.workoutActive()).isTrue();
        controller.cancelWorkout(null);
        assertThat(controller.workoutActive()).isFalse();
    }

    @Test
    public void addsExercise(){
        controller.startWorkout("test");
        controller.addExercise(new Exercise());
        Workout workout = controller.getWorkout();
        assertThat(workout.getExercises().size()).isEqualTo(1);
    }

    @Test
    public void addsSet(){
        controller.startWorkout("test");
        controller.addExercise(new Exercise());
        controller.addSet(0);
        Workout workout = controller.getWorkout();
        assertThat(workout.getExercises().get(0).getSets().size()).isEqualTo(2);
    }

    @Test
    public void startsFromPreset(){
        Workout workout = new Workout("preset");
        workout.setPreset(true);
        controller.startWorkoutFromPreset(workout);
        assertThat(controller.workoutActive()).isTrue();
        Workout activeWorkout = controller.getWorkout();
        assertThat(activeWorkout.getName()).isEqualTo("preset");
        assertThat(activeWorkout.preset()).isFalse();
        assertThat(activeWorkout.getWorkoutStarted()).isNotNull();
    }

    @Test
    public void getsExerciseTypes(){
        List<ExerciseType> listFromAPI = api.getAllExerciseTypes();
        List<ExerciseType> listFromController = controller.getExerciseTypes();
        assertThat(listFromController.size()).isEqualTo(listFromAPI.size());
    }

    @Test
    public void deletesExerciseType(){
        controller.deleteExerciseType("test-id");
        assertThat(api.getRecentParam()).isEqualTo("test-id");
    }

    @Test
    public void findsUser(){
        User user = controller.findUser();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test");
    }

    @Test
    public void getsPresetWorkouts(){
        List<Workout> list = controller.getPresetWorkouts();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void getsNonPresetWorkouts(){
        List<Workout> list = controller.getNonPresetWorkouts();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void deletesWorkout(){
        Workout workout = new Workout("test");
        workout.setId("test-id");
        controller.deleteWorkout(workout);
        assertThat(api.getRecentParam()).isEqualTo("test-id");
    }

    @Test
    public void exerciseTypeExistsWorks(){
        assertThat(controller.exerciseTypeExists("test")).isTrue();
        assertThat(controller.exerciseTypeExists("not a real exercise type")).isFalse();
    }

    @Test
    public void createsExerciseType(){
        ExerciseType type = new ExerciseType("test2");
        controller.createExerciseType(type);
        assertThat(api.getRecentParam()).isInstanceOf(ExerciseType.class);
        ExerciseType typeFromAPI = (ExerciseType) api.getRecentParam();
        assertThat(typeFromAPI.getName()).isEqualTo("test2");
    }

    @Test
    public void makesWorkoutPreset(){
        Workout workout = new Workout("test");
        workout.setId("test-id");
        controller.makePreset(workout);
        assertThat(api.getRecentParam()).isInstanceOf(Workout.class);
        List<Workout> workouts = controller.getPresetWorkouts();
        assertThat(workouts.size()).isEqualTo(1);
        Workout preset = workouts.get(0);
        assertThat(preset.preset()).isTrue();
    }

    @Test
    public void registersUser(){
        User user = new User("test", "password");
        controller.registerUserAsync(user);
        assertThat(api.getRecentParam()).isInstanceOf(User.class);
        User userFromAPI = (User) api.getRecentParam();
        assertThat(userFromAPI.getUsername()).isEqualTo("test");
        assertThat(userFromAPI.getPassword()).isEqualTo("password");
    }

    @Test
    public void authenticateUser() {
        User user = new User("test", "password");
        controller.authenticateUserAsync(user);
        assertThat(api.getRecentParam()).isInstanceOf(User.class);
        User userFromAPI = (User) api.getRecentParam();
        assertThat(userFromAPI.getUsername()).isEqualTo("test");
        assertThat(userFromAPI.getPassword()).isEqualTo("password");
    }

    @Test
    public void getUsers(){
        TrainerAPIWrapper mockApi = mock(TrainerAPIWrapper.class);
        TrainerController mockController = new WorkoutController(mockApi);
        when(mockApi.getUsernames()).thenReturn(Arrays.asList("test", "test2"));

        List<String> users = mockController.getUsernames();

        assertThat(users.size()).isEqualTo(2);
        verify(mockApi, times(1)).getUsernames();
    }

}

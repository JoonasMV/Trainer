package com.example.trainer;

import com.example.trainer.diagram.api.TrainerAPIWrapper;
import com.example.trainer.diagram.api.controllers.TrainerController;
import com.example.trainer.diagram.api.controllers.WorkoutController;
import com.example.trainer.mock.MockAPI;
import com.example.trainer.diagram.api.model.Exercise;
import com.example.trainer.diagram.api.model.ExerciseType;
import com.example.trainer.diagram.api.model.User;
import com.example.trainer.diagram.api.model.Workout;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    public void savesWorkout() throws InterruptedException {
        controller.startWorkout("test");
        controller.saveWorkout();
        Thread.sleep(100);
        assertThat(controller.workoutActive()).isFalse();
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
    public void deletesExerciseType() throws InterruptedException {
        controller.deleteExerciseType("test-id");
        Thread.sleep(100);
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
    public void deletesWorkout() throws InterruptedException {
        Workout workout = new Workout("test");
        workout.setId("test-id");
        controller.deleteWorkout(workout);
        Thread.sleep(100);
        assertThat(api.getRecentParam()).isEqualTo("test-id");
    }

    @Test
    public void exerciseTypeExistsWorks(){
        assertThat(controller.exerciseTypeExists("test")).isTrue();
        assertThat(controller.exerciseTypeExists("not a real exercise type")).isFalse();
    }

    @Test
    public void createsExerciseType() throws InterruptedException {
        ExerciseType type = new ExerciseType("test2");
        controller.createExerciseType(type);
        Thread.sleep(100);
        assertThat(api.getRecentParam()).isInstanceOf(ExerciseType.class);
        ExerciseType typeFromAPI = (ExerciseType) api.getRecentParam();
        assertThat(typeFromAPI.getName()).isEqualTo("test2");
    }

    @Test
    public void makesWorkoutPreset() throws InterruptedException {
        Workout workout = new Workout("test");
        workout.setId("test-id");
        controller.makePreset(workout);
        Thread.sleep(100);
        assertThat(api.getRecentParam()).isInstanceOf(Workout.class);
        List<Workout> workouts = controller.getPresetWorkouts();
        assertThat(workouts.size()).isEqualTo(1);
        Workout preset = workouts.get(0);
        assertThat(preset.preset()).isTrue();
    }

    @Test
    public void registersUser() throws ExecutionException, InterruptedException {
        User user = new User("test", "password");
        Future<Boolean> res =  controller.registerUserAsync(user);
        res.get();
        assertThat(api.getRecentParam()).isInstanceOf(User.class);
        User userFromAPI = (User) api.getRecentParam();
        assertThat(userFromAPI.getUsername()).isEqualTo("test");
        assertThat(userFromAPI.getPassword()).isEqualTo("password");
    }

    @Test
    public void authenticateUser() throws ExecutionException, InterruptedException {
        User user = new User("test", "password");
        Future<Boolean> res = controller.authenticateUserAsync(user);
        res.get();
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

package com.example.trainer;

import com.example.trainer.controllers.TrainerController;
import com.example.trainer.controllers.WorkoutController;
import com.example.trainer.mock.MockAPI;
import com.example.trainer.model.Exercise;
import com.example.trainer.model.ExerciseType;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class ControllerTest {
    private final MockAPI api = new MockAPI();
    private final TrainerController controller = new WorkoutController(api);

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
        assertThat(workout.getExercises().get(0).getSets().size()).isEqualTo(1);
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
        controller.deleteWorkout(workout);
        assertThat(api.getRecentParam()).isInstanceOf(Workout.class);
        Workout workoutFromAPI = (Workout) api.getRecentParam();
        assertThat(workoutFromAPI.getName()).isEqualTo("test");
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





}

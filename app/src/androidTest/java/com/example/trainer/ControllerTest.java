package com.example.trainer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.database.DatabaseHelper;
import com.example.trainer.database.dao.framework.DevelopmentDAO;
import com.example.trainer.database.dao.framework.IExerciseTypeDAO;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.dao.framework.IWorkoutDAO;
import com.example.trainer.database.dao.sqlite.BetterSqliteDAOFactory;
import com.example.trainer.schemas.Exercise;
import com.example.trainer.schemas.ExerciseSet;
import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.schemas.Workout;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ControllerTest {

    private static TrainerController workoutController;
    private static final String WORKOUT_NAME = "TEST";
    private static final String WORKOUT_ID = "workoutId";
    private static final String EXERCISE_ID = "exerciseId";
    private static final String EXERCISE_NAME = "squat";
    private static final String DUMMY_EXTYPE_ID = "exerciseTypeId";
    private static final int SET_REPS = 10;
    private static final double SET_WEIGHT = 50;

    private static Context ctx;

    private static final BetterSqliteDAOFactory daoFactory = new BetterSqliteDAOFactory();

    private static DevelopmentDAO devdao;

    private static IExerciseTypeDAO exerciseTypeDAO;

    private static IWorkoutDAO workoutDAO;

    @BeforeClass
    public static void setup(){
        ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseHelper.initialize(ctx);
        workoutController = BaseController.getController();
        devdao = DevelopmentDAO.newInstance();
        exerciseTypeDAO = daoFactory.createExerciseTypeDAO();
        workoutDAO = daoFactory.createWorkoutDAO();
    }

    @Before
    public void beforeEach(){
        workoutController.cancelWorkout(ctx);
        devdao.clearDatabase();
        createMockExerciseTypes();
    }

    @Test
    public void clear_database(){
        devdao.clearDatabase();
        List<ExerciseType> types = exerciseTypeDAO.getAll();
        assertEquals(0, types.size());
    }

    private void createMockExerciseTypes(){
        ExerciseType mockType = new ExerciseType(EXERCISE_NAME);
        mockType.set_id(DUMMY_EXTYPE_ID);
        exerciseTypeDAO.save(mockType);
    }

    @Test
    public void workout_starts(){
        workoutController.startWorkout(WORKOUT_NAME);
        Workout started = workoutController.getWorkout();
        assertNotNull(started);
        assertEquals(WORKOUT_NAME, started.getName());
    }

    @Test
    public void workout_cancels(){
        workoutController.startWorkout(WORKOUT_NAME);
        workoutController.cancelWorkout(ctx);
        Workout workout = workoutController.getWorkout();
        assertNull(workout);
    }

    @Test
    public void workout_is_saved(){
        workoutController.startWorkout(WORKOUT_NAME);
        Workout workoutToBeSaved = workoutController.getWorkout();
        workoutToBeSaved.setId(WORKOUT_ID);
        workoutController.saveWorkout();
        IWorkoutDAO dao = daoFactory.createWorkoutDAO();
        List<Workout> workouts = dao.getAll();
        System.out.println("WORKOUTS --- " + workouts);
        assertEquals(1, workouts.size());
        Workout workout = workouts.get(0);
        assertEquals(WORKOUT_NAME, workout.getName());
    }

    @Test
    public void workout_name_changes(){
        workoutController.startWorkout(WORKOUT_NAME);
        workoutController.changeWorkoutName("new");
        Workout workout = workoutController.getWorkout();
        assertEquals("new", workout.getName());
    }

    @Test
    public void workout_isActive(){
        workoutController.startWorkout(WORKOUT_NAME);
        assertTrue(workoutController.workoutActive());
        workoutController.cancelWorkout(ctx);
        assertFalse(workoutController.workoutActive());
    }

    @Test
    public void exercise_added(){
        workoutController.startWorkout(WORKOUT_NAME);
        createExercise();

        Workout workout = workoutController.getWorkout();

        assertEquals(1, workout.getExList().size());

        Exercise exFromWorkout = workout.getExList().get(0);

        assertEquals(EXERCISE_NAME, exFromWorkout.getExerciseName());
    }

    @Test
    public void set_gets_added(){
        workoutController.startWorkout(WORKOUT_NAME);
        createExercise();
        workoutController.addSet(0);
        ExerciseSet set = workoutController.getWorkout().getExList().get(0).getSetList().get(0);
        assertNotNull(set);
    }

    private void createExercise(){
        ExerciseType type = exerciseTypeDAO.getExerciseTypeByName(EXERCISE_NAME);
        Exercise exercise = new Exercise(type.get_id());
        exercise.setExerciseId(EXERCISE_ID);
        System.out.println(exercise);
        workoutController.addExercise(exercise);
    }

    @Test
    public void write_and_read_from_pref(){
        workoutController.startWorkout(WORKOUT_NAME);
        workoutController.saveToPref(ctx);
        workoutController.setWorkout(null);
        assertFalse(workoutController.workoutActive());
        workoutController.readFromPref(ctx);
        assertTrue(workoutController.workoutActive());
        assertEquals(WORKOUT_NAME, workoutController.getWorkout().getName());
    }

    @Test
    public void start_from_preset(){
        Workout workout = new Workout(WORKOUT_NAME);
        workout.setId(WORKOUT_ID);
        workout.setPreset(true);
        workoutController.startWorkoutFromPreset(workout);
        assertTrue(workoutController.workoutActive());
        assertFalse(workoutController.getWorkout().isPreset());
        assertEquals(WORKOUT_NAME, workoutController.getWorkout().getName());
    }

    @Test
    public void retrieves_exerciseTypes(){
        ExerciseType type = new ExerciseType("bench");
        exerciseTypeDAO.save(type);

        List<ExerciseType> types = workoutController.getExerciseTypes();

        assertEquals(2, types.size());
    }

    @Test
    public void deletes_exerciseType(){
        ExerciseType typeFromDb = exerciseTypeDAO.getExerciseTypeByName(EXERCISE_NAME);
        workoutController.deleteExerciseType(typeFromDb.get_id());

        List<ExerciseType> types = exerciseTypeDAO.getAll();
        assertEquals(0, types.size());
    }

    @Test
    public void creates_user(){
        createUser();
        IUserDAO userDAO = daoFactory.createUserDAO();
        User userFromDb = userDAO.getUser();
        assertEquals("mock", userFromDb.getUsername());
    }

    private void createUser(){
        User newUser = new User("mock");
        workoutController.createUser(newUser);
    }


    @Test
    public void finds_user(){
        createUser();
        User user = workoutController.findUser();
        assertEquals("mock", user.getUsername());
    }

    @Test
    public void retrieves_preset_workouts(){
        createPresetWorkout();
        List<Workout> presets = workoutController.getPresetWorkouts();
        assertEquals(1, presets.size());
        assertEquals("preset", presets.get(0).getName());
    }

    private void createPresetWorkout(){
        Workout workout = new Workout("preset", new Date());
        workout.setId(WORKOUT_ID);
        workout.ended();
        workout.setPreset(true);
        workoutDAO.save(workout);
    }

    @Test
    public void retrieves_non_preset_workouts(){
        createNonPresetWorkout();
        createPresetWorkout();
        List<Workout> nonPresets = workoutController.getNonPresetWorkouts();
        assertEquals(1, nonPresets.size());
        assertEquals("nonPreset", nonPresets.get(0).getName());
    }

    private void createNonPresetWorkout(){
        Workout workout = new Workout("nonPreset", new Date());
        workout.setId(WORKOUT_ID);
        workout.ended();
        workoutDAO.save(workout);
    }

    @Test
    public void deletes_workout(){
        createNonPresetWorkout();
        List<Workout> workouts = workoutDAO.getAll();
        Workout workout = workouts.get(0);
        workoutController.deleteWorkout(workout);
        List<Workout> shouldBeEmpty = workoutDAO.getAll();
        assertTrue(shouldBeEmpty.isEmpty());
    }

    @Test
    public void exerciseTypeExists_returns_correct_values(){
        boolean shouldNotExist = workoutController.exerciseTypeExists("invalid");
        assertFalse(shouldNotExist);
        boolean shouldExist = workoutController.exerciseTypeExists(EXERCISE_NAME);
        assertTrue(shouldExist);
    }

    @Test
    public void creates_exerciseType(){
        ExerciseType newType = new ExerciseType("NEW");
        workoutController.createExerciseType(newType);
        List<ExerciseType> types = exerciseTypeDAO.getAll();
        assertEquals(2, types.size());
    }

    @Test
    public void makes_preset_workout(){
        Workout workout = new Workout("BEST", new Date());
        workout.setId(WORKOUT_ID);
        workout.ended();
        workoutController.makePreset(workout);
        List<Workout> workouts = workoutDAO.getPresets();
        assertEquals(1, workouts.size());
        assertEquals("BEST", workouts.get(0).getName());
    }

}

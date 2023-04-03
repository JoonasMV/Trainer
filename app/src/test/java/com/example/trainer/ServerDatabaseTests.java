package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.trainer.schemas.ExerciseType;
import com.example.trainer.schemas.User;
import com.example.trainer.serverConnector.services.DevService;
import com.example.trainer.serverConnector.Server;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ServerDatabaseTests {
    private static Server server;
    private static DevService devServer;

    @BeforeClass
    public static void init() {
        server = Server.getInstance();
        devServer = new DevService();

    }

    @Before
    public void beforeEach() {
        devServer.clearDatabase();
    }

    @Test
    public void usersCanBeAdded() {
        User user = new User("test user");
        User addedUser = server.user().save(user);
        assertEquals("test user", addedUser.getUsername());
        assertNotNull(addedUser.getId());
    }

    @Test
    public void userCanBeFoundByID() {
        User addedUser = addUserToDb();
        String userId = addedUser.getId();
        User userById = server.user().getById(userId);
        assertNotNull(userById);
        assertEquals(addedUser.getUsername(), userById.getUsername());
    }

    @Test
    public void userListCanBeFound() {
        addUserToDb();
        addUserToDb();
        List<User> userList = server.user().getAll();
        assertEquals(userList.size(),2);
    }

    private User addUserToDb() {
        User user = new User("test user");
        return server.user().save(user);
    }

    @Test
    public void exerciseTypesCanBeAdded() {
        ExerciseType exerciseType = new ExerciseType("test exerciseType");
        ExerciseType addedExercise = server.exerciseType().save(exerciseType);
        assertEquals("test exerciseType", addedExercise.getName());
        assertNotNull(addedExercise.getId());
    }

//    @Test
//    public void exerciseTypesCanBeGetByName() {
//
//    }
}

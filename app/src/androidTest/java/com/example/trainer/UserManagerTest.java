package com.example.trainer;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.model.User;
import com.example.trainer.util.UserManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserManagerTest {

    private Context context = InstrumentationRegistry.getInstrumentation().getContext();
    private UserManager userManager;

    @Before
    public void beforeEach(){
        userManager = new UserManager(context);
        userManager.logout();
    }

    @Test
    public void getUser(){
        assertNull(userManager.getUser());
        userManager.setUser(new User("test", "password"));
        User user = userManager.getUser();
        assertEquals("test", user.getUsername());
    }

    @Test
    public void isUserLoggedIn(){
        assertFalse(userManager.isUserLoggedIn());
        userManager.setUser(new User("test", "password"));
        assertTrue(userManager.isUserLoggedIn());
    }

    @Test
    public void logout(){
        userManager.setUser(new User("test", "password"));
        assertTrue(userManager.isUserLoggedIn());
        userManager.logout();
        assertFalse(userManager.isUserLoggedIn());
    }

    @Test
    public void readAndWriteUserToPref(){
        userManager.setUser(new User("test", "password"));
        User user = userManager.readUserFromPref();
        assertEquals("test", user.getUsername());
        assertTrue(userManager.isUserLoggedIn());
    }

    @Test
    public void clearUserPref(){
        userManager.setUser(new User("test", "password"));
        assertTrue(userManager.isUserLoggedIn());
        userManager.clearUserPref();
        User user = userManager.readUserFromPref();
        assertNull(user);
    }

}

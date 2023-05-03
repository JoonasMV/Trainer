package com.example.trainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.trainer.diagram.api.util.TokenManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TokenManagerTest {

    private final Context context = InstrumentationRegistry.getInstrumentation().getContext();
    private TokenManager tokenManager;

    @Before
    public void beforeEach(){
        tokenManager = new TokenManager(context);
        tokenManager.deleteToken();
    }

    @Test
    public void getToken(){
        assertNull(tokenManager.getToken());
        tokenManager.saveToken("test");
        String token = tokenManager.getToken();
        assertEquals("test", token);
    }

    @Test
    public void saveToken(){
        tokenManager.saveToken("test");
        String token = tokenManager.getToken();
        assertEquals("test", token);
    }

    @Test
    public void deleteToken(){
        tokenManager.saveToken("test");
        String token = tokenManager.getToken();
        assertEquals("test", token);
        tokenManager.deleteToken();
        assertNull(tokenManager.getToken());
    }
}

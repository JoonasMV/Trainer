package com.example.trainer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.trainer.api.TrainerAPIWrapper;
import com.example.trainer.model.User;
import com.example.trainer.util.TokenManager;
import com.example.trainer.util.UserManager;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito.*;
import org.robolectric.RobolectricTestRunner;

import okhttp3.OkHttpClient;

@RunWith(RobolectricTestRunner.class)
public class APIWrapperTest {

    private static TrainerAPIWrapper api;
    private static OkHttpClient client = mock(OkHttpClient.class);
    private static TokenManager tokenManager = mock(TokenManager.class);
    private static Gson gson = mock(Gson.class);
    private static UserManager userManager = mock(UserManager.class);

    @BeforeClass
    public static void setup(){
        api = new TrainerAPIWrapper(client, tokenManager, gson, userManager);
    }

    @Test
    public void register(){
        User user = new User("test", "password");
        api.registerUser(user);

//        verify(client, times(1)).newCall(any());
    }
}

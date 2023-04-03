package com.example.trainer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.trainer.schemas.Workout;
import com.google.gson.Gson;

public class WorkoutSerializer {

    private final static String key = "workout_key";
    private static final String fileKey = "trainer";

    private WorkoutSerializer(){

    }

    /**
     * Reads and deserializes workout from sharedPreferences
     * @param context Context of app
     * @return  Deserialized workout or null if workout was not found
     */

    public static Workout readWorkoutFromPref(Context context){
        SharedPreferences pref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        String value = pref.getString(key, null);
        if(value == null){
            return null;
        }
        return deserialize(value);
    }

    private static Workout deserialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Workout.class);
    }


    public static void writeWorkoutToPref(Workout workout, Context context){
        String json = serialize(workout);
        Editor prefsEditor = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(key, json).commit();
    }

    private static String serialize(Workout workout){
        Gson gson = new Gson();
        return gson.toJson(workout);
    }

    public static void clearPrefs(Context context){
        SharedPreferences pref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        pref.edit().clear().commit();
    }

}

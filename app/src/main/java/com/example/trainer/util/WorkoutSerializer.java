package com.example.trainer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.trainer.model.Workout;
import com.google.gson.Gson;

/**
 * Utility class for serializing and deserializing active workout to/from sharedPreferences
 */
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
        if(context == null){
            return null;
        }
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


    /**
     * Saves workout to sharedPreferences
     * @param workout Workout to be serialized and written
     * @param context Context of app
     */
    public static void writeWorkoutToPref(Workout workout, Context context){
        if(context == null){
            return;
        }
        String json = serialize(workout);
        Editor prefsEditor = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(key, json).apply();
    }

    private static String serialize(Workout workout){
        Gson gson = new Gson();
        return gson.toJson(workout);
    }

    /**
     * Clears sharedPreferences
     * @param context Context of app
     */
    public static void clearPrefs(Context context){
        if (context == null) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }

}

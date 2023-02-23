package com.example.trainer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.trainer.R;
import com.example.trainer.database.schemas.Workout;
import com.google.gson.Gson;

public class WorkoutSerializer {

    private final static String key = "workout_key";

    private WorkoutSerializer(){

    }

    /***
     * Reads and deserializes workout from sharedPreferences
     * @param context Context of app
     * @return  Deserialized workout or null if workout was not found
     */

    public static Workout readWorkoutFromPref(Context context){
        String fileKey = context.getString(R.string.preference_file_key);
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

        String fileKey = context.getString(R.string.preference_file_key);
        Editor prefsEditor = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    private static String serialize(Workout workout){
        Gson gson = new Gson();
        return gson.toJson(workout);
    }

}

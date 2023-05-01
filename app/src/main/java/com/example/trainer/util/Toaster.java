package com.example.trainer.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    private final static int duration = Toast.LENGTH_SHORT;

    public static void toast(Context context, String text){
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public static void longToast(Context context, String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }

}

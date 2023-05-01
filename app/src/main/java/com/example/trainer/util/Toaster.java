package com.example.trainer.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    private static Toast currentToast;

    private final static int duration = Toast.LENGTH_SHORT;

    public static void toast(Context context, String text){
        if(currentToast != null){
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, text, duration);
        currentToast.show();

    }

    public static void longToast(Context context, String text){
        if(currentToast != null){
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        currentToast.show();
    }


}

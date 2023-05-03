package com.example.trainer.diagram.api.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class for showing toasts
 */
public class Toaster {

    private Toaster(){}
    private static Toast currentToast;

    private final static int duration = Toast.LENGTH_SHORT;

    /**
     * Shows a toast with the given text
     * @param context the context of the app
     * @param text the text of the toast
     */
    public static void toast(Context context, String text){
        if(currentToast != null){
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, text, duration);
        currentToast.show();

    }

    /**
     * Shows a long toast with the given text
     * @param context the context of the app
     * @param text the text of the toast
     */
    public static void longToast(Context context, String text){
        if(currentToast != null){
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        currentToast.show();
    }


}

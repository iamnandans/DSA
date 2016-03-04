package com.pivot.dsa;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shanthan on 2/28/2016.
 */
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

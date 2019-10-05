package com.example.mainproject.UtilityClasses;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Message {
    public static void message(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,100);
        toast.show();
    }
}

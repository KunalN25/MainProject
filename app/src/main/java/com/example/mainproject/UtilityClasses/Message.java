package com.example.mainproject.UtilityClasses;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Message {
    public static void message(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,100);
        toast.show();
    }
    public static void message(Context context,String message,int lonORshort){
        Toast toast;
        if(lonORshort==0)
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        else
           toast=Toast.makeText(context,message,Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER,0,100);
        toast.show();
    }

}

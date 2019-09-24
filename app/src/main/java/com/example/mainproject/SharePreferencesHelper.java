package com.example.mainproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePreferencesHelper {

    private SharedPreferences sharedPreferences;
    public SharePreferencesHelper(Context context){
        sharedPreferences=context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }
    public  void addToPreference(String key,String data){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,data);
        editor.apply();
        Log.d("MainActivity","Added to preference");
    }
    public String loadPreferences(String key){
        Log.d("MainActivity","Loaded from preference");

        return (sharedPreferences.getString(key,""));   //The second parameter is the string to be returned when the string
                                                            //with the key is not found.

    }
    public void clearPreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public boolean checkParticularKey(String key){
        if(sharedPreferences.contains(key))
            return true;
        else
            return false;
    }
}

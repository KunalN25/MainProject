package com.example.mainproject.UtilityClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;

public class SharePreferencesHelper {

    private SharedPreferences sharedPreferences;
    private static String TAG="share";
    public SharePreferencesHelper(Context context){
        sharedPreferences=context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }
    public  void addToPreference(String key,String data){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,data);
        editor.apply();
        Log.d(TAG, "addToPreference: added pref "+data);
    }
    public String loadPreferences(String key){
        Log.d(TAG,"Loaded from preference "+key);

        return (sharedPreferences.getString(key,""));   //The second parameter is the string to be returned when the string
                                                            //with the key is not found.

    }
    public void clearPreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Log.d(TAG, "clearPreferences: ");
    }
    public boolean checkParticularKey(String key){
        if(sharedPreferences.contains(key))
            return true;
        else
            return false;
    }
}

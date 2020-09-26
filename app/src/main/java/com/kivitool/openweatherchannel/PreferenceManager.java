package com.kivitool.openweatherchannel;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {


    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences("name",Context.MODE_PRIVATE);
    }

    public void putString(String key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }

    public void putInteger(String key, int value){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInteger(String key){

        return sharedPreferences.getInt(key, 0);

    }

    public void putFloat(String key, float value){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key){

        return sharedPreferences.getFloat(key, 0f);

    }

    public void putBoolean(String key, boolean value){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(key, value);
        editor.apply();

    }

    public boolean getBoolean(String key){

        return sharedPreferences.getBoolean(key, false);

    }

}

package com.example.puneet.smarttravellerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

 class Session
{
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context ctx;

    Session(Context ctx1)
    {
        ctx = ctx1;
        prefs = ctx.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
        Log.d("smart traveller","in Session() constructor of Session class - creating session for user");
    }

    void setLoggedin(boolean logggedin)
    {
        editor.putBoolean("loggedInMode", logggedin);
        editor.commit();
        Log.d("smart traveller","in setSession() of Session class - creating session for user");
    }

    boolean loggedin()
    {
        Log.d("smart traveller","in session() of Session class - by default session is false");
        return prefs.getBoolean("loggedInMode", false);
    }
}

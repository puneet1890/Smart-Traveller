package com.example.puneet.smarttravellerapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class User
{
    private static String name;
    private Context context;

    User(Context ctx) { context = ctx; }

    public static void setName(String name)
    {
        User.name = name;
    }

    public static String getName()
    {
        return name;
    }

}
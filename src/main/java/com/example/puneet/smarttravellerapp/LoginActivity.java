package com.example.puneet.smarttravellerapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends Activity
{
    EditText et_Login_Email, et_Login_Pass;
    Button btn_Login_Login, btn_Login_Register;

    DBHelper dbHelper;
    Session session;
    FusedLocationProviderClient client;
    String sourceLatitude,sourceLongitude;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("smart traveller", "in onCreate of LoginActivity");

        dbHelper = new DBHelper(this);
        session = new Session(this);

        et_Login_Email = findViewById(R.id.et_Login_Email);
        et_Login_Pass = findViewById(R.id.et_Login_Pass);

        btn_Login_Login = findViewById(R.id.btn_Login_Login);
        btn_Login_Register = findViewById(R.id.btn_Login_Register);

        if (session.loggedin()) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String value = location.toString();
                    Log.d("smart traveller", "Your current Location: " +value);

                    String[] latlongi = value.split(",");
                    String lati = latlongi[0];
                    String longi = latlongi[1];

                    sourceLatitude = lati.substring(15);
                    sourceLongitude = longi.substring(0, 9);

                    Log.d("smart traveller", "Latitude: " + sourceLatitude);
                    Log.d("smart traveller", "Longitude: " + sourceLongitude);
                }
            }
        });
    }

    public void login_register(View view)
    {
        Log.d("smart traveller","You are entering registration page");

        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }

    public void login_login(View view)
    {
        String email = et_Login_Email.getText().toString();
        String pass = et_Login_Pass.getText().toString();

        if (dbHelper.getUser(email,pass))
        {
            Log.d("smart traveller","You are logged in, navigating to User Dashboard");

            session.setLoggedin(true);

            Toast.makeText(getApplicationContext(), "Your Current Location is " + sourceLatitude + "," + sourceLongitude, Toast.LENGTH_LONG).show();

            intent = new Intent(getApplicationContext(),DashboardActivity.class);
            intent.putExtra("lati", sourceLatitude);
            intent.putExtra("longi",sourceLongitude);
            startActivity(intent);

            //finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Wrong email/password",Toast.LENGTH_LONG).show();
        }
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }
}

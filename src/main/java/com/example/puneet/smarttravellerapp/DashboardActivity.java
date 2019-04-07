package com.example.puneet.smarttravellerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.List;

public class DashboardActivity extends Activity
{
    Button btn_Dashboard_Logout, btn_Dashboard_Navigate;
    EditText et_Dashboard_Numofhours, et_Dashboard_Location;
    ListView lv;

    private Session session;
    ArrayAdapter<Distance> adapter;
    List<Distance> bangalore;
    String location1, latitude, longitude;
    String lati,longi;
    String sourceLatitude,sourceLongitude;
    String num_of_hours;
    private DBDistanceHelper db;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new Session(this);
        String name = User.getName();

        Log.d("smart traveller", "Welcome to UserDashboard  --> in onCreate() of UserDashboard");

        db = new DBDistanceHelper(this);

        btn_Dashboard_Logout = findViewById(R.id.btn_Dashboard_Logout);
        btn_Dashboard_Navigate = findViewById(R.id.btn_Dashboard_Navigate);

        et_Dashboard_Numofhours = findViewById(R.id.et_Dashboard_Numofhours);
        et_Dashboard_Location = findViewById(R.id.et_Dashboard_Location);

        lv = findViewById(R.id.list_Dashboard_Display);

        Log.d("smart traveller", "Adding data to the table");

        Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_LONG).show();

        in = getIntent();
        lati = in.getStringExtra("lati");
        longi = in.getStringExtra("longi");

        Log.d("smart traveller", "Your current location is "+lati+" ,"+longi);

        if (!session.loggedin())
        {
            dashboard_logout(btn_Dashboard_Logout);
        }
    }

    public void search(View view)
    {
        Log.d("smart traveller", "in search() of Dashboard Activity");

        location1 = et_Dashboard_Location.getText().toString();
        num_of_hours = et_Dashboard_Numofhours.getText().toString();

        if (location1 == null || location1.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Enter the location you want to explore, it cannot be blank", Toast.LENGTH_LONG).show();
        } else if (num_of_hours == null || num_of_hours.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Enter num of hours you want to explore, it cannot be blank", Toast.LENGTH_LONG).show();
        } else {
            bangalore = db.getPlaces(location1, num_of_hours);

            Log.d("smart traveller", "in search() of Dashboard Activity,data from database " + bangalore);
            //Toast.makeText(getApplicationContext(),"Getting data from DBDistanceHelper "+bangalore.toString(),Toast.LENGTH_LONG).show();

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bangalore);
            lv.setAdapter(adapter);

            Log.d("smart traveller", "in search() of Dashboard Activity,data from database " + bangalore);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("smart traveller", "you clicked the list item");

                    Toast.makeText(getApplicationContext(), bangalore.get(position).getLatitude() + "," + bangalore.get(position).getLongitude(), Toast.LENGTH_SHORT).show();

                    latitude = bangalore.get(position).getLatitude();
                    longitude = bangalore.get(position).getLongitude();
                }
            });
        }
    }

    public void dashboard_navigate(View view)
    {
        Log.d("smart traveller", "you are navigating to MapsActivity");

           //navigating based on User Location/Landmark
          // highlight all the places on map, obtained from List
         // navigate to the selected place
        // highlight the landmarks on the map

        sourceLatitude = lati;
        sourceLongitude = longi;

        Toast.makeText(getApplicationContext(),"Your Current Location is "+sourceLatitude+","+sourceLongitude,Toast.LENGTH_LONG).show();
        Log.d("smart traveller",sourceLatitude+","+sourceLongitude);
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

        intent.putExtra("source_latitude",sourceLatitude);
        intent.putExtra("source_longitude",sourceLongitude);
        intent.putExtra("destination_latitude",latitude);
        intent.putExtra("destination_longitude",longitude);
        intent.putExtra("List of places",(Serializable) bangalore);
        startActivity(intent);
    }

    public void dashboard_logout(View view)
    {
        Log.d("smart traveller","you logged out of Dashboard");

        session.setLoggedin(false);
        finish();

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}

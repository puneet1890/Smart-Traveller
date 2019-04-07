package com.example.puneet.smarttravellerapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    double sourceLatitude;
    double sourceLongitude;
    double destinationLatitude;
    double destinationLongitude;
    List<Distance> list;

    String sourceLati,sourceLongi,destiLati,destiLongi;
    GoogleMap mMap;
    String place_lati,place_longi;
    double places_lati,places_longi;
    //MarkerOptions place1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

            Intent in = getIntent();
            sourceLati = in.getStringExtra("source_latitude");
            sourceLongi = in.getStringExtra("source_longitude");
            destiLati = in.getStringExtra("destination_latitude");
            destiLongi = in.getStringExtra("destination_longitude");

            Toast.makeText(getApplicationContext(),"Your Location "+sourceLati+","+sourceLongi,Toast.LENGTH_LONG).show();
            Log.d("smart traveller","Your current Location is: "+sourceLati+","+sourceLongi);
            Log.d("smart traveller","Your destination is: "+destiLati+","+destiLongi);

            sourceLatitude = Double.parseDouble(sourceLati);
            sourceLongitude = Double.parseDouble(sourceLongi);
            destinationLatitude = Double.parseDouble(destiLati);
            destinationLongitude = Double.parseDouble(destiLongi);

            list = (List<Distance>) in.getSerializableExtra("List of places");

                for (Distance d :list)
                {
                    place_lati = d.getLatitude();
                    place_longi = d.getLongitude();

                    places_lati = Double.parseDouble(place_lati);
                    places_longi = Double.parseDouble(place_longi);
                    //coordinates.add(new LatLng(places_lati, places_longi));
                }


                  //  place1 = new MarkerOptions().position(new LatLng(places_lati,places_longi));

            Toast.makeText(getApplicationContext(),"your destination is "+destinationLatitude+","+destinationLongitude,Toast.LENGTH_LONG).show();

            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", sourceLatitude, sourceLongitude, "From Home", destinationLatitude, destinationLongitude, "To Destination");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException ex)
            {
                try
                {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(unrestrictedIntent);
                }
                catch(ActivityNotFoundException innerEx)
                {
                    Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
                }
            }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        for(Distance distance : list)
        {
            String lati = distance.getLatitude();
            String longi = distance.getLongitude();
            String place = distance.getPlace();

            places_lati = Double.parseDouble(lati);
            places_longi = Double.parseDouble(longi);

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(places_lati,places_longi))
                    .title(place));
        }
        //mMap.addMarker(place1);
        // Add a marker in Bangalore and move the camera
        LatLng bang = new LatLng(sourceLatitude, sourceLongitude);
        mMap.addMarker(new MarkerOptions().position(bang).title("Bangalore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bang));

    }
}

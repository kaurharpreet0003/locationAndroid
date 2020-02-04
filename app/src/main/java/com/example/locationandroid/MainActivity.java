package com.example.locationandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private  static final int REQUEST_LOCATION = 1;

    Button getLocationBtn;
    TextView showLocationTxt;
    LocationManager locationManager;
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocationTxt = findViewById(R.id.show_location);
        getLocationBtn = findViewById(R.id.getLocation);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                //check if gps is on
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    //write function to on gps
                    onGPS();
                }else{
                    // gps is already on
                    getLocation();
                }
            }
        });
    }

    private void getLocation(){
        //check permissions again
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,  Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGPS != null){
                double lat = LocationGPS.getLatitude();
                double longi = LocationGPS.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                showLocationTxt.setText("Your location:" + "/n" + "Latitude = " + latitude + "\n" + "Longitude = " + longitude);
            } else if (LocationNetwork!= null){
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                showLocationTxt.setText("Your location:" + "/n" + "Latitude = " + latitude + "\n" + "Longitude = " + longitude);
            } else if (LocationPassive != null){
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                showLocationTxt.setText("Your location:" + "/n" + "Latitude = " + latitude + "\n" + "Longitude = " + longitude);
            }else {
                Toast.makeText(this, "Can't get your Location", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void onGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

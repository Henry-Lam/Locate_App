package com.example.locationapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapStart extends Activity {

    Context context;
    LocationListener locationListener;
    LocationManager locationManager;

    MapStart (Context context, LocationListener locationListener, LocationManager locationManager){
        this.context = context;
        this.locationListener = locationListener;
        this.locationManager = locationManager;
    }

    public void checkMapPermission(){
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else{
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }
    }

    public void requestMapPermissionResult(@NonNull int[] grantResults){
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //if location permission is granted then do this

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, locationListener);
                //2nd input: time (ms) --> how long before a location update
                //3rd input: Distance --> how far u travel before the location is updated again
                //The last input is a Location Listener object, and this method runs all the methods in that object (updates the location)
            }
        }
    }


}

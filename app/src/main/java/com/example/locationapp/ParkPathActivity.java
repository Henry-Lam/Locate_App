package com.example.locationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParkPathActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng currentLocation;
    LatLng parkedLocation;
    MapView mapView;
    DataGetter dataGetter;
    ActivityTransition activityTransition;
    int indexClicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_path);

        dataGetter = new DataGetter(this);
        dataGetter.updateArrays();
        Intent intent = getIntent();
        indexClicked = intent.getIntExtra("indexClicked", -1);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();     //Clears the markers on map

                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));

                parkedLocation = new LatLng(dataGetter.getLatArray().get(indexClicked), dataGetter.getLngArray().get(indexClicked));

                mMap.addMarker(new MarkerOptions()
                        .position(parkedLocation)
                        .title("Parked Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        checkMapPermission();

    }
    public void checkMapPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // If permission is not granted, ask for permission
            // Once u click allow then this package will always be PERMISSION_GRANTED (even if u run it again)

            ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //the 3rd input (1) is just a request code used to keep track of things
        } else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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

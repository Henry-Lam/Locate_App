package com.example.locationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ActivityTransition activityTransition;
    MapStart mapStart;

    LocationManager locationManager;
    LocationListener locationListener;
    MapView mapView;
    LatLng currentLocation;

    String locationUrl;
    boolean firstLoad;

    TextView urlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        urlTextView = findViewById(R.id.urlTextView);
        firstLoad = true;
        activityTransition = new ActivityTransition(this);

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

                if (firstLoad){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
                    firstLoad = false;
                }
                currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                updateUrlTextView(urlTextView);
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
        mapStart = new MapStart(this, locationListener, locationManager);
        mapStart.checkMapPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mapStart.requestMapPermissionResult(grantResults);
    }

    public void copyUrl(View view){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", locationUrl);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Copied " + locationUrl, Toast.LENGTH_SHORT).show();

    }

    public void updateUrlString(){
        locationUrl = "https://www.google.com/maps/place/";
        locationUrl += (Math.round(currentLocation.latitude * 1000000)) / 1000000.0;
        locationUrl += ",%20";
        locationUrl += (Math.round(currentLocation.longitude * 1000000)) / 1000000.0;
    }

    public void updateUrlTextView(TextView textView){
        updateUrlString();
        textView.setText(locationUrl);
    }
}

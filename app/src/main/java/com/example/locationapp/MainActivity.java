package com.example.locationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDataBase;
    ActivityTransition activityTransition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityTransition = new ActivityTransition(this);

        myDataBase = this.openOrCreateDatabase("parkingsDB", MODE_PRIVATE, null);
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS parkings (id INTEGER PRIMARY KEY, lat DECIMAL(10, 8), lng DECIMAL(11, 8), date VARCHAR)");
        // DECIMAL (num1, num2)
        // num1 = number of digits stored
        // num2 = number of digits after decimal point
        // Latitude ranges from -90 to +90 (degrees), so DECIMAL(10, 8) (ex: 05.00000000)
        // longitudes range from -180 to +180 (degrees) so DECIMAL(11, 8) (ex: 005.00000000)
    }



    public void goParkSave (View view){
        activityTransition.go(ParkActivity.class);
    }

    public void goParkList (View view){
        activityTransition.go(ParkListActivity.class);
    }

    public void goMyLocation (View view) {
        activityTransition.go(MyLocationActivity.class);
    }


}



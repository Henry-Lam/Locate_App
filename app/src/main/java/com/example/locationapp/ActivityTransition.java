package com.example.locationapp;

import android.content.Context;
import android.content.Intent;

public class ActivityTransition {

    Context context;
    Intent intent;

    ActivityTransition (Context context){
        this.context = context;
    }

    public void goMain (){
        go(MainActivity.class);
    }

    public void go (Class activity){
        // Parameter Ex: MainActivity.class
        this.intent = new Intent(this.context, activity);
        this.context.startActivity(this.intent);
    }

    public void goWithIntExtra (Class activity, int index){
        this.intent = new Intent(this.context, activity);
        this.intent.putExtra("indexClicked", index);
        this.context.startActivity(this.intent);
    }
}

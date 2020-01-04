package com.example.locationapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DataGetter {

    private ArrayList<String> dateArray;
    private ArrayList<Double> latArray;
    private ArrayList<Double> lngArray;
    private ArrayList<Integer> idArray;
    private Cursor c;

    SQLiteDatabase myDataBase;
    Context context;

    DataGetter(Context context){
        this.context = context;
        myDataBase = this.context.openOrCreateDatabase("parkingsDB", MODE_PRIVATE, null);

        dateArray = new ArrayList<String>();
        latArray = new ArrayList<Double>();
        lngArray = new ArrayList<Double>();
        idArray = new ArrayList<Integer>();
    }

    public void updateArrays(){
        c = myDataBase.rawQuery("SELECT * FROM parkings ORDER BY id DESC",null);

        boolean hasData = c.moveToFirst();

        while (hasData){
            this.dateArray.add(c.getString(c.getColumnIndex("date")));
            this.latArray.add(c.getDouble(c.getColumnIndex("lat")));
            this.lngArray.add(c.getDouble(c.getColumnIndex("lng")));
            this.idArray.add(c.getInt(c.getColumnIndex("id")));

            hasData = c.moveToNext();
        }
    }

    public ArrayList<String> getDateArray() {
        return dateArray;
    }

    public ArrayList<Double> getLatArray() {
        return latArray;
    }

    public ArrayList<Double> getLngArray() {
        return lngArray;
    }

    public ArrayList<Integer> getIdArray() {
        return idArray;
    }
}

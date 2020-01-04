package com.example.locationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class ParkListActivity extends AppCompatActivity {

    SQLiteDatabase myDataBase;
    ListView listViewParking;
    DataGetter dataGetter;
    ActivityTransition activityTransition;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_list);

        listViewParking = findViewById(R.id.listViewParking);
        myDataBase = this.openOrCreateDatabase("parkingsDB", MODE_PRIVATE, null);

        dataGetter = new DataGetter(this);
        dataGetter.updateArrays();

        final ArrayAdapter<String> dateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataGetter.getDateArray());
        listViewParking.setAdapter(dateArrayAdapter);

        activityTransition = new ActivityTransition(this);

        listViewParking.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activityTransition.goWithIntExtra(ParkPathActivity.class, i);
            }
        });

        context = this;
        listViewParking.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final PopupMenu popupMenu = new PopupMenu(context, view);
                // input 2: location where the popup menu, pops out from
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Delete")){
                            int idToDel = dataGetter.getIdArray().get(i);
                            myDataBase.execSQL("DELETE FROM parkings WHERE id = '"+idToDel+"'");

                            dataGetter.getDateArray().remove(i);
                            dataGetter.getIdArray().remove(i);
                            dataGetter.getLatArray().remove(i);
                            dataGetter.getLngArray().remove(i);
                            dateArrayAdapter.notifyDataSetChanged();
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }
}

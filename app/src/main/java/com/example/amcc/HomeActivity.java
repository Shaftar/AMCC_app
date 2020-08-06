package com.example.amcc;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;



import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {

    // References to feed our custom List Adapter object
    String[] nameListArray = {"First Function", "Second Function"};
    String[] infoListArray = {"info about first function.", "info about second function."};
    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};
    //Create list View
    private ListView listView;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Trigger a menu item by setting item clickListener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.iHome:
                        // Do something
                        break;
                    case R.id.iShare:
                        // Do something
                        break;
                    case R.id.iSet:
                        // Do something
                        break;
                }
                return false;
            }
        });


        // Create Custom Adapter here
        CustomListAdapter homeListAdapter = new CustomListAdapter(this, nameListArray, infoListArray, imgListArrayID);
        // Link listView to our CustomListAdapter
        listView = (ListView) findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

}

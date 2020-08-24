package com.example.amcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amcc.R;
import com.example.amcc.adapter.CustomListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeActivity extends BaseActivity {

    // References to feed our custom List Adapter object
    String[] nameListArray = {"First Function", "Second Function"};
    String[] infoListArray = {"info about first function.", "info about second function."};
    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private void setListHomeAdapter() {

        // Create Custom Adapter here
        CustomListAdapter homeListAdapter = new CustomListAdapter(this, nameListArray, infoListArray, imgListArrayID);
        // Link listView to our CustomListAdapter
        //Create list View
        ListView listView = findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);
        // Create EventListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent mainActivity = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                        break;
                    case 1:
                        try {
                            compare();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set List Adapter
        setListHomeAdapter();
    }

    private void compare() throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        Date d1 = sdformat.parse("5.2.2019");
        Date fixed = sdformat.parse("10.12.2018");
        System.out.println("The date 1 is: " + sdformat.format(d1));
        if (d1.compareTo(fixed) < 0) {
            System.out.println("The date 1 is older: ");
            return;
        }
        System.out.println("sdjfoij");


    }
}

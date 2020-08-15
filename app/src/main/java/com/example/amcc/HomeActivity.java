package com.example.amcc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import adapter.CustomListAdapter;
import model.ApiDataModel;
import model.CarDetails;
import model.FuelType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitApi.RetrofitClient;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Debug";
    // References to feed our custom List Adapter object
    String[] nameListArray = {"First Function", "Second Function"};
    String[] infoListArray = {"info about first function.", "info about second function."};
    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};
    //Create list View
    private ListView listView;
    private CustomListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    private void shareIt() {
        //sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Thank you for sharing our App.";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share it");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, null));

    }

    private void setupSetting() {
        // set up Settings activity
        Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void createToolBar() {

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
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
                        return true;
                    case R.id.iShare:
                        // Do something
                        shareIt();
                        return true;
                    case R.id.iSet:
                        // Do something
                        setupSetting();
                        return true;
                    default:
                        return HomeActivity.super.onOptionsItemSelected(item);
                }
            }
        });
    }

    private void setListHomeAdapter() {

        // Create Custom Adapter here
        homeListAdapter = new CustomListAdapter(this, nameListArray, infoListArray, imgListArrayID);
        // Link listView to our CustomListAdapter
        listView = (ListView) findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);
        // Create EventListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarDetails car = new CarDetails("bremen", 1211, 122, FuelType.e5,
                        "15.07.2010", 5.5, 6);
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "First Function", Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                        break;
                    case 1:
                        getCosts(car);
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
        // Find the toolbar view inside the activity layout
        createToolBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void getCosts(CarDetails car) {

        RetrofitClient client = RetrofitClient.getINSTANCE();
        client.getCosts(car).enqueue(new Callback<ApiDataModel>() {
            @Override
            public void onResponse(Call<ApiDataModel> call, Response<ApiDataModel> response) {
                if (response.code() == 200)
                    Log.d(TAG, "object: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<ApiDataModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}

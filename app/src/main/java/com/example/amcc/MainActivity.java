package com.example.amcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.ApiDataModel;
import model.CarDetails;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import util.HttpHandling;

public class MainActivity extends AppCompatActivity {

    // City List tools
    private ArrayAdapter spinnerAdapter;
    private Spinner spinner;
    private ArrayList cityNameArray;
    private TextView cityName;
    private static final String DEBUG_TAG = "NetworkStatus: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interface);

        // Create the Toolbar
        createToolBar();

        //Create City Name List
        setUpCityNamesList();

        // Get Date Input
        getDateInput();


    }
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }


    //For debugging purposes
    public void networkStatus() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn |= networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn |= networkInfo.isConnected();
                }

            } else {
                // not connected to the internet
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
    }
    // For check the Network Connection before
    // we request data
    public boolean checkNetwork() {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                return true;
            }
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }
    // Send Request to server and fetch the data
    private void createNetworking(RequestParams params) {
        if (checkNetwork()) {
            Log.d("App: CreateNetworking", "Network is working- Internet connected!");
            HttpHandling.get(HttpHandling.API_URL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("App: CreateNetworking", "OnSuccess- has response!");
                    ApiDataModel apiDataModel = ApiDataModel.fromJson(response);
                    CarDetails carDetails = CarDetails.fromJason(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    // If the response is JSONObject Failed
                    Log.d("App: onFailure", "---------------- No response! : " +
                            errorResponse.toString());
                    Toast.makeText(MainActivity.this,
                            "Request Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Values input user edit activity
    private void getFromUserEdit(int regYear, int engineSize, int co2, int fuelType) {

        RequestParams params = new RequestParams();
        params.put("", regYear);
        params.put("", engineSize);
        params.put("", co2);
        params.put("", fuelType);
        createNetworking(params);

    }

    //User Input values activity
    private void getFromUserInput(CarDetails carDetails) {

        RequestParams params = new RequestParams();
        params.put("", carDetails.getCity());
        params.put("", carDetails.getFirst_register_date());
        params.put("", carDetails.getEmission());
        params.put("", carDetails.getFuel_type());
        createNetworking(params);
    }
    private void setUpCityNamesList() {

        spinner = (Spinner) findViewById(R.id.spinnerCityHolderID);
        cityNameArray = new ArrayList();
        cityNameArray.add("Bremen");
        cityNameArray.add("Berlin");
        cityNameArray.add("Hamburg");
        cityNameArray.add("Bayern");
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cityNameArray);
        spinner.setAdapter(spinnerAdapter);
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
                        Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(homeActivity);
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
                        return MainActivity.super.onOptionsItemSelected(item);
                }
            }
        });
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
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private void getDateInput() {

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarViewID);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String days, months, years;
                days = String.valueOf(dayOfMonth);
                months = String.valueOf(month);
                years = String.valueOf(year);
                Toast.makeText(getApplicationContext(), ""+days+"-"+months+"-"+years, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
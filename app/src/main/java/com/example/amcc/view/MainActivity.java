package com.example.amcc.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.amcc.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private Spinner spinner;
    private EditText dateField, emissionEdtField, engineSizeField, avgConField, milePerYField;
    private String citySelected, days, months, years;
    private static final String DEBUG_TAG = "NetworkStatus: ";
    private ImageView dateImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interface);

         //Create City Name List
        setUpCityNamesList();

        //Initial Fields
        initialEditField();

        // Get Date Input
        getClaenderByClick();


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


    private void setUpCityNamesList() {

        spinner = findViewById(R.id.spinnerCityHolderID);
        ArrayList cityNameArray = new ArrayList();
        cityNameArray.add("Bremen");
        cityNameArray.add("Berlin");
        cityNameArray.add("Hamburg");
        cityNameArray.add("Bayern");
        // City List tools
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cityNameArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                citySelected = spinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "City: "+citySelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDateInput() {

        dateField = findViewById(R.id.edtDateID);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.calender_view);
        CalendarView calendarView = dialog.findViewById(R.id.calendarViewID);
        dialog.setTitle("Date");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                days = String.valueOf(dayOfMonth);
                months = String.valueOf(month);
                years = String.valueOf(year);
                String dateFormat = "" + days + "-" + months + "-" + years;
                Toast.makeText(getApplicationContext(), dateFormat, Toast.LENGTH_SHORT).show();
                dateField.setText(dateFormat);
            }
        });
        dialog.show();
        dialog.setCancelable(true);
    }

    private void moveEnteredValueToResultActivity() {

        EditText emissionNum = findViewById(R.id.edtNumEmissionID);
        int emNum = Integer.parseInt(emissionNum.getText().toString());
        EditText engSizeNum = findViewById(R.id.edtNumEngSizeID);
        int engSiNum = Integer.parseInt(engSizeNum.getText().toString());
        EditText avgConNum = findViewById(R.id.edtNumConsumeID);
        int avgCon = Integer.parseInt(avgConNum.getText().toString());
        EditText mileNum = findViewById(R.id.edtNumMileAgeYearID);
        int milePerYear = Integer.parseInt(mileNum.getText().toString());

        Intent userInputIntentData = new Intent(MainActivity.this, ResultActivity.class);
        userInputIntentData.putExtra("City", citySelected);
        String regDate = days+"-"+months+"-"+years;
        userInputIntentData.putExtra("First Register Date", regDate);
        userInputIntentData.putExtra("Emission", emNum);
        userInputIntentData.putExtra("Engine Size", engSiNum);
        userInputIntentData.putExtra("Average Consume", avgCon);
        userInputIntentData.putExtra("Mileage Per Year", milePerYear);
        startActivity(userInputIntentData);
    }

    public void getResult(View view) {

        // Check user Input and get result
        checkUserInput();
    }

    private void checkUserInput() {

        // Reset errors displayed in the form.
        emissionEdtField.setError(null);
        engineSizeField.setError(null);
        avgConField.setError(null);
        milePerYField.setError(null);

        // Store values at the time of the entered vlaues
        String emission = emissionEdtField.getText().toString();
        String engSize = engineSizeField.getText().toString();
        String avgCon = avgConField.getText().toString();
        String milePerY = milePerYField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(emission)) {
            emissionEdtField.setError(getString(R.string.error_field_required));
            focusView = emissionEdtField;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(milePerY)) {
            milePerYField.setError(getString(R.string.error_field_required));
            focusView = milePerYField;
            cancel = true;
        } if (TextUtils.isEmpty(engSize)) {
            engineSizeField.setError(getString(R.string.error_field_required));
            focusView = engineSizeField;
            cancel = true;
        } if (TextUtils.isEmpty(avgCon)) {
            avgConField.setError(getString(R.string.error_field_required));
            focusView = avgConField;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't get Result and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Start Result Activity
            moveEnteredValueToResultActivity();
        }
    }

    private void initialEditField() {

        emissionEdtField = findViewById(R.id.edtNumEmissionID);
        avgConField = findViewById(R.id.edtNumConsumeID);
        engineSizeField = findViewById(R.id.edtNumEngSizeID);
        milePerYField = findViewById(R.id.edtNumMileAgeYearID);

    }

    private void getClaenderByClick() {


        dateImageView = findViewById(R.id.dateIconID);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateInput();
            }
        });
    }

}
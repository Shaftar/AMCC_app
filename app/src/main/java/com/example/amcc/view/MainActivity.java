package com.example.amcc.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.amcc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends BaseActivity {

    private Spinner spinner;
    public static String CITY_SELECTED;
    SimpleDateFormat simpleDateFormat;
    private TextView euro4TxtView, emissionTxtView, engSizeTxtView, avgConTxtView, mileTxtView, _000KmTxtView;
    private EditText dateField, emissionEdtField, engineSizeField, avgConField, milePerYField;
    private RadioGroup radioGroup;
    //Create Calender Dialog
    private Dialog dialog;
    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interface);
        //Create City Name List
        setUpCityNamesList();

        initialField();
        setItemInvisible();
        getCalenderByClick();
        getEditInputDate();
        getFuelType();


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
            if (e.getMessage() != null) {
                Log.e("Connectivity Exception", e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }


    private void setUpCityNamesList() {

        spinner = findViewById(R.id.spinnerCityHolderID);
        ArrayList<String> cityNameArray = new ArrayList<>();
        cityNameArray.add("Bremen");
        cityNameArray.add("Berlin");
        cityNameArray.add("Hamburg");
        cityNameArray.add("Bayern");
        // City List tools
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityNameArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CITY_SELECTED = spinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), "City: "+citySelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initialField() {

        calendarView = findViewById(R.id.calendarViewID);
        simpleDateFormat = new SimpleDateFormat("dd-mm-yy");
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.calender_view);
        calendarView = dialog.findViewById(R.id.calendarViewID);
        dateField = findViewById(R.id.edtDateID);
        // EditText
        emissionEdtField = findViewById(R.id.edtNumEmissionID);
        avgConField = findViewById(R.id.edtNumConsumeID);
        engineSizeField = findViewById(R.id.edtNumEngSizeID);
        milePerYField = findViewById(R.id.edtNumMileAgeYearID);

        // TextView
        euro4TxtView = findViewById(R.id.txtViewEuro4);
        emissionTxtView = findViewById(R.id.txtViewEmissionID);
        avgConTxtView = findViewById(R.id.txtViewConsumID);
        engSizeTxtView = findViewById(R.id.txtViewEngineId);
        mileTxtView = findViewById(R.id.txtviewDistancePerYreaID);
        _000KmTxtView = findViewById(R.id.txtView1000kmID);

        // RadioBtnGroup
        radioGroup = findViewById(R.id.BtnGroupID);
    }

    public void getResult(View view) {
        // Check user Input and get result
        checkUserInput();
    }

    private void getCalenderByClick() {

        ImageView dateImageView = findViewById(R.id.dateIconID);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputDate();
            }
        });
    }

    private void getEditInputDate() {

        dateField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0) {
                    setItemInvisible();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                String dateFromEdtField = dateField.getText().toString();
                getViewByDate(dateFromEdtField);
                //Toast.makeText(getApplicationContext(), dateFromEdtField, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getInputDate() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String days = String.valueOf(dayOfMonth);
                String months = String.valueOf(month);
                String years = String.valueOf(year);
                String dateFormUser = days + "-" + months + "-" + years;
                dateField.setText(dateFormUser);
                getViewByDate(dateFormUser);
                Toast.makeText(getApplicationContext(), dateFormUser, Toast.LENGTH_SHORT).show();
                dateField.setText(dateFormUser);
                dialog.dismiss();
            }
        });
        dialog.setTitle("Date");
        dialog.setCancelable(true);
        dialog.show();
    }

    private void getViewByDate(String date) {

        try {
            Date date1 = simpleDateFormat.parse(date);
            //Dates To compare
            String date04_11_2008 = "04-11-2008";
            Date date2 = simpleDateFormat.parse(date04_11_2008);
            String date05_11_2008 = "05-11-2008";
            Date date3 = simpleDateFormat.parse(date05_11_2008);
            String date30_06_2009 = "30-06-2009";
            Date date4 = simpleDateFormat.parse(date30_06_2009);
            String date01_07_2009 = "01-07-2009";
            Date date5 = simpleDateFormat.parse(date01_07_2009);

            if (date1.after(date5) || date1.equals(date5)) {

                // After 01/07/2009
                emissionTxtView.setVisibility(View.VISIBLE);
                emissionEdtField.setVisibility(View.VISIBLE);
                engSizeTxtView.setVisibility(View.VISIBLE);
                engineSizeField.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                // Unvisible
                euro4TxtView.setVisibility(View.GONE);
            } else if (date1.before(date2) || date1.equals(date2)) {

                // Before 04/11/2008
                euro4TxtView.setVisibility(View.VISIBLE);
                engSizeTxtView.setVisibility(View.VISIBLE);
                engineSizeField.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);

                //Unvisible
                emissionTxtView.setVisibility(View.GONE);
                emissionEdtField.setVisibility(View.GONE);
            } else if (date1.after(date3) && date1.before(date4)
                    || date1.equals(date3)
                    || date1.equals(date4)) {

                //After 05/11/2008
                //Before 30/06/2009
                euro4TxtView.setVisibility(View.VISIBLE);
                emissionTxtView.setVisibility(View.VISIBLE);
                emissionEdtField.setVisibility(View.VISIBLE);
                engSizeTxtView.setVisibility(View.VISIBLE);
                engineSizeField.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                Log.d("Date", e.getMessage());
            }

        }
    }

    private void getFuelType() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.e5BtnID:
                        String fuele5 = "e5";
                        Toast.makeText(getApplicationContext(), fuele5, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dieselBtnID:
                        String fuelDiesel = "diesel";
                        Toast.makeText(getApplicationContext(), fuelDiesel, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.e10BtnID:
                        String fuel10 = "e10";
                        Toast.makeText(getApplicationContext(), fuel10, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setItemInvisible() {

        //EditText
        emissionEdtField.setVisibility(View.GONE);
        avgConField.setVisibility(View.GONE);
        engineSizeField.setVisibility(View.GONE);
        milePerYField.setVisibility(View.GONE);

        //TextView
        euro4TxtView.setVisibility(View.GONE);
        emissionTxtView.setVisibility(View.GONE);
        avgConTxtView.setVisibility(View.GONE);
        engSizeTxtView.setVisibility(View.GONE);
        mileTxtView.setVisibility(View.GONE);
        _000KmTxtView.setVisibility(View.GONE);

        // RadioButtonGroup
        radioGroup.setVisibility(View.GONE);
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
        }
        if (TextUtils.isEmpty(engSize)) {
            engineSizeField.setError(getString(R.string.error_field_required));
            focusView = engineSizeField;
            cancel = true;
        }
        if (TextUtils.isEmpty(avgCon)) {
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
            if (checkNetwork())
                moveEnteredValueToResultActivity(MainActivity.CITY_SELECTED);

        }
    }

    private void moveEnteredValueToResultActivity(String city) {

        int emNum = Integer.parseInt(emissionEdtField.getText().toString());
        int engSiNum = Integer.parseInt(engineSizeField.getText().toString());
        double avgCon = Integer.parseInt(avgConField.getText().toString());
        int milePerYear = Integer.parseInt(milePerYField.getText().toString());


        Intent userInputIntentData = new Intent(MainActivity.this, ResultActivity.class);
        userInputIntentData.putExtra("City", city);
        String regDate = "";
        userInputIntentData.putExtra("First Register Date", regDate);
        userInputIntentData.putExtra("Emission", emNum);
        userInputIntentData.putExtra("Engine Size", engSiNum);
        userInputIntentData.putExtra("Average Consume", avgCon);
        userInputIntentData.putExtra("Mileage Per Year", milePerYear);
        startActivity(userInputIntentData);
    }
}
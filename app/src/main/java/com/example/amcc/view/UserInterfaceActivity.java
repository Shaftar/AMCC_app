package com.example.amcc.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.model.FuelType;
import com.example.amcc.util.ApiController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserInterfaceActivity extends BaseActivity {

    private Spinner spinner;
    public static String CITY_SELECTED;
    SimpleDateFormat simpleDateFormat;
    private TextView euro4TxtView, emissionTxtView, engSizeTxtView, avgConTxtView, mileTxtView, _000KmTxtView;
    private EditText dateField, emissionEdtField, engineSizeField, avgConField, milePerYField;
    private RadioGroup radioGroup;
    private RadioButton diesel, e5, e10;
    //Create Calender Dialog
    private Dialog dialog;
    private CalendarView calendarView;
    private String dateFormUser, fuelType, emission, engSize, avgCon, milePerY, RegDate;
    private ProgressBar progressBar;
    private int engSiNumIn, emissionNumIn, milePerYearIn, spinnerPosition;
    private double avgConD;
    private boolean radioBtnChecked, clearInstance;

    @Override
    public void onSaveInstanceState(@NonNull Bundle userInputIntentData, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(userInputIntentData, outPersistentState);

        userInputIntentData.putString("City", CITY_SELECTED);
        userInputIntentData.putString("First Register Date", dateFormUser);
        userInputIntentData.putString("Emission", emission);
        userInputIntentData.putString("Engine Size", engSize);
        userInputIntentData.putString("Average Consume", avgCon);
        userInputIntentData.putString("Mileage Per Year", milePerY);
        userInputIntentData.putString("Fuel Type", fuelType);
        userInputIntentData.putInt("spinner position", spinnerPosition);
        userInputIntentData.putBoolean("radioBtn Checked", radioBtnChecked);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle fromUserInterface) {
        super.onRestoreInstanceState(fromUserInterface);
        String city, date, fuelType, engSize, emission, avgCo, mileYear;
        spinnerPosition = fromUserInterface.getInt("spinner position");
        radioBtnChecked = fromUserInterface.getBoolean("radioBtn Checked");
        city = fromUserInterface.getString("City");
        date = fromUserInterface.getString("First Register Date");
        engSize = fromUserInterface.getString("Engine Size");
        emission = fromUserInterface.getString("Emission");
        avgCo = fromUserInterface.getString("Average Consume");
        mileYear = fromUserInterface.getString("Mileage Per Year");
        fuelType = fromUserInterface.getString("Fuel Type");

        //Set Text To
        dateField.setText(date);
        emissionEdtField.setText(emission);
        engineSizeField.setText(engSize);
        avgConField.setText(avgCo);
        milePerYField.setText(mileYear);
        getViewByDate(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
        if (savedInstanceState != null && clearInstance) {
            savedInstanceState.clear();
        }
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
        spinner.setSelection(spinnerPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerPosition = position;
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


        progressBar = findViewById(R.id.progressBarUserInterfaceID);
        progressBar.setVisibility(View.GONE);
        calendarView = findViewById(R.id.calendarViewID);
        simpleDateFormat = new SimpleDateFormat("dd.mm.yy");
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
        diesel = findViewById(R.id.dieselBtnID);
        e5 = findViewById(R.id.e5BtnID);
        e10 = findViewById(R.id.e10BtnID);
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
                dateFormUser = days + "." + months + "." + years;
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
            String date04_11_2008 = "04.11.2008";
            Date date2 = simpleDateFormat.parse(date04_11_2008);
            String date05_11_2008 = "05.11.2008";
            Date date3 = simpleDateFormat.parse(date05_11_2008);
            String date30_06_2009 = "30.06.2009";
            Date date4 = simpleDateFormat.parse(date30_06_2009);
            String date01_07_2009 = "01.07.2009";
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
                euro4TxtView.setText(R.string.euro4_higher);
                euro4TxtView.setTextColor(Color.BLUE);
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
                euro4TxtView.setText(R.string.euro4_higher);
                euro4TxtView.setTextColor(Color.BLUE);
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
                if (radioBtnChecked) {
                    RadioButton radioButton = findViewById(checkedId);
                }

                switch (checkedId) {
                    case R.id.e5BtnID:
                        fuelType = "e5";
                        if (e5.isChecked())
                            radioBtnChecked = true;
                        //Toast.makeText(getApplicationContext(), fuelType, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dieselBtnID:
                        fuelType = "diesel";
                        if (diesel.isChecked())
                            radioBtnChecked = true;
                        //Toast.makeText(getApplicationContext(), fuelType, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.e10BtnID:
                        fuelType = "e10";
                        if (e10.isChecked())
                            radioBtnChecked = true;
                        //Toast.makeText(getApplicationContext(), fuelType, Toast.LENGTH_SHORT).show();
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
        dateField.setError(null);
        emissionEdtField.setError(null);
        engineSizeField.setError(null);
        avgConField.setError(null);
        milePerYField.setError(null);

        // Store values at the time of the entered vlaues
        //Get Text From Edit Text
        emission = emissionEdtField.getText().toString();
        engSize = engineSizeField.getText().toString();
        avgCon = avgConField.getText().toString();
        milePerY = milePerYField.getText().toString();
        RegDate = dateField.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(emission)) {
            emissionEdtField.setError(getString(R.string.error_field_required));
            focusView = emissionEdtField;
            cancel = true;
        }

        // Check for a valid email address.
//        if (TextUtils.isEmpty(milePerY)) {
//            milePerYField.setError(getString(R.string.error_field_required));
//            focusView = milePerYField;
//            cancel = true;
//        }
        if (TextUtils.isEmpty(engSize)) {
            engineSizeField.setError(getString(R.string.error_field_required));
            focusView = engineSizeField;
            cancel = true;
        }
//        if (TextUtils.isEmpty(avgCon)) {
//            avgConField.setError(getString(R.string.error_field_required));
//            focusView = avgConField;
//            cancel = true;
//        }
        if (TextUtils.isEmpty(RegDate)) {
            dateField.setError(getString(R.string.error_field_required));
            focusView = dateField;
            cancel = true;
            timer(focusView);
            euro4TxtView.setText(R.string.date_error_message);
            euro4TxtView.setTextColor(Color.RED);
            euro4TxtView.setPadding(0, 100, 0, 0);
            euro4TxtView.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    euro4TxtView.setVisibility(View.GONE);
                }
            };
            //Timer Tools
            // 1 Second
            int interval = 3000;
            handler.postDelayed(runnable, interval);

        }
        if (cancel) {
            // There was an error; don't get Result and focus the first
            // form field with an error.
            focusView.requestFocus();
            timer(focusView);

        } else {

            euro4TxtView.setVisibility(View.GONE);
            // Start Result Activity
            if (checkNetwork())
                moveEnteredValueToResultActivity();

        }
    }

    private void moveEnteredValueToResultActivity() {

        //Get Text From Edit Text
        emission = emissionEdtField.getText().toString();
        engSize = engineSizeField.getText().toString();
        avgCon = avgConField.getText().toString();
        milePerY = milePerYField.getText().toString();
        RegDate = dateField.getText().toString();
        emissionNumIn = Integer.parseInt(emissionEdtField.getText().toString());
        engSiNumIn = Integer.parseInt(engineSizeField.getText().toString());
        if (!TextUtils.isEmpty(avgCon) || !TextUtils.isEmpty(milePerY)) {
            avgConD = Double.parseDouble(avgConField.getText().toString());
            milePerYearIn = Integer.parseInt(milePerYField.getText().toString());
        }


        Intent userInputIntentData = new Intent(UserInterfaceActivity.this, ResultActivity.class);
        userInputIntentData.putExtra("City", CITY_SELECTED);
        userInputIntentData.putExtra("First Register Date", dateFormUser);
        userInputIntentData.putExtra("Emission", emission);
        userInputIntentData.putExtra("Engine Size", engSize);
        userInputIntentData.putExtra("Average Consume", avgCon);
        userInputIntentData.putExtra("Mileage Per Year", milePerY);
        userInputIntentData.putExtra("Fuel Type", fuelType);
        //Create Car Object
        if (TextUtils.isEmpty(avgCon) || TextUtils.isEmpty(milePerY)) {

            avgConD = 6.0;
            milePerYearIn = 10000;
            CarDetails car = new CarDetails(CITY_SELECTED, engSiNumIn,
                    emissionNumIn, fuelType, dateFormUser, avgConD, milePerYearIn);
            getCosts(car);
            startActivity(userInputIntentData);
        }
    }

    private void timer(View view) {
        //Set Timer
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                view.clearFocus();
            }
        };
        //Timer Tools
        // After 1 Second clear focus view
        int interval = 3000;
        handler.postDelayed(runnable, interval);
    }

    public void getCosts(CarDetails car) {

        //Create Api controller to fetch data
        progressBar.setVisibility(View.VISIBLE);
        ApiController controller = new ApiController(this, car);
        setItemInvisible();
        Bundle bundle = new Bundle();
        Intent result = new Intent(UserInterfaceActivity.this, ResultActivity.class);
        //result.putExtra("ApiResponse", controller.getApiDataModel());
        bundle.putParcelable("ApiResponse", controller.getApiDataModel());
        result.putExtras(bundle);
        startActivity(result);
    }

    private void getClearIntent() {
        Intent intent = getIntent();
        clearInstance = intent.getBooleanExtra("clearInstance", true);
    }
}
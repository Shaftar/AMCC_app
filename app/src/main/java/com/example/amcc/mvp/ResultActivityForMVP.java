package com.example.amcc.mvp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.example.amcc.R;
import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.example.amcc.view.BaseActivity;


public class ResultActivityForMVP extends BaseActivity implements View.OnClickListener, CatTaxPresenter.ViewTaxPresenter {

    private static final String TAG = "ResultActivity";
    private TextView txtViewUserMsgFirst, txtViewFuelPrice, txtViewFuelPriceValue,
            txtViewAnnualTax, txtViewAnnualValue, txtViewFuelCost, txtViewFuelCostValue,
            txtViewUserMsgLast, txtViewCityValue, txtViewRegDateValue,
            txtViewUserMsgShowInput, txtViewCity, txtViewRegDate,
            txtViewEngSize, txtViewEngSizeValue, txtViewFuelType, txtViewFuelTypeValue,
            txtViewEmission, txtViewEmissionValue, txtViewAvgCon, txtViewavgConeValue,
            txtViewMileAgeValue, txtViewMileAge;
    private ProgressBar progressBar;
    private CatTaxPresenter catTaxPresenter;
    private Button retryBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Initial Item
        initialItem();

        getProgressInfoBar();


        sendQueryToController();

        showUserInput();

    }

    private void initialItem() {

        // TextView
        txtViewUserMsgFirst = findViewById(R.id.resultTxtViewUserMessageID);
        txtViewFuelPrice = findViewById(R.id.resultFuelPriceTxtViewID);
        txtViewFuelCost = findViewById(R.id.resultFuelCostTxtViewID);
        txtViewAvgCon = findViewById(R.id.resultTxtViewAvgConID);
        txtViewMileAge = findViewById(R.id.resultTxtViewMileAgeYearID);
        txtViewAnnualTax = findViewById(R.id.resultAnnualTaxTxtViewID);
        txtViewUserMsgLast = findViewById(R.id.resultUniversalTxtView);
        txtViewUserMsgShowInput = findViewById(R.id.resultTxtViewShowUserInputID);
        txtViewCity = findViewById(R.id.resultTxtViewCityID);
        txtViewRegDate = findViewById(R.id.resultTxtViewRegDateID);
        txtViewEngSize = findViewById(R.id.resultTxtViewEngSizeID);
        txtViewEmission = findViewById(R.id.resultTxtViewEmissionID);
        txtViewFuelType = findViewById(R.id.resultTxtViewFuelID);
        txtViewavgConeValue = findViewById(R.id.resultAvgConValueID);
        txtViewAnnualValue = findViewById(R.id.resultAnnualValueID);
        txtViewCityValue = findViewById(R.id.resultCityValueID);
        txtViewEmissionValue = findViewById(R.id.resultEmissionValueID);
        txtViewEngSizeValue = findViewById(R.id.resultEngSizeValueID);
        txtViewFuelCostValue = findViewById(R.id.resultFuelCostValueID);
        txtViewFuelPriceValue = findViewById(R.id.resultFuelPriceValueID);
        txtViewFuelTypeValue = findViewById(R.id.resultFuelTypeValueID);
        txtViewRegDateValue = findViewById(R.id.resultRegDateValueID);
        txtViewMileAgeValue = findViewById(R.id.resultMileYearValueID);

        //Button
        progressBar = findViewById(R.id.progressBarResultInfoID);
        Button newRequestBtn = findViewById(R.id.btnResultNewID);
        Button editBtn = findViewById(R.id.btnResultEditID);
        retryBtn = findViewById(R.id.retryBtnID);
        retryBtn.setOnClickListener(this);
        retryBtn.setVisibility(View.GONE);
        newRequestBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        txtViewUserMsgLast.setOnClickListener(this);

    }

    private void setItemInvisible() {

        txtViewUserMsgFirst.setVisibility(View.GONE);
        txtViewFuelPrice.setVisibility(View.GONE);
        txtViewAnnualTax.setVisibility(View.GONE);
        txtViewFuelCost.setVisibility(View.GONE);
        txtViewUserMsgLast.setVisibility(View.GONE);
        txtViewFuelCostValue.setVisibility(View.GONE);
        txtViewFuelPriceValue.setVisibility(View.GONE);
        txtViewAnnualValue.setVisibility(View.GONE);

//        txtViewUserMsgShowInput.setVisibility(View.GONE);
//        txtViewCity.setVisibility(View.GONE);
//        txtViewRegDate.setVisibility(View.GONE);
//        txtViewEngSize.setVisibility(View.GONE);
//        txtViewAvgCon.setVisibility(View.GONE);
//        txtViewMileAge.setVisibility(View.GONE);
//        txtViewFuelType.setVisibility(View.GONE);
//        txtViewEmission.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnResultNewID:
                Intent userInterface = new Intent(ResultActivityForMVP.this, UserInterfaceActivityForMVP.class);
                boolean setNewRequest = true;
                userInterface.putExtra("clearInstance", true);
                startActivity(userInterface);
                break;
            case R.id.btnResultEditID:
                Intent editIntent = new Intent(ResultActivityForMVP.this, UserInterfaceActivityForMVP.class);
                startActivity(editIntent);
//                clickToRate();
                break;
            case R.id.retryBtnID:
                catTaxPresenter.onRefresh();
                getProgressInfoBar();
                break;
            case R.id.resultUniversalTxtView:
                clickToRate();
        }
    }

    private void showUserInput() {


        Intent fromUserInterface = getIntent();
        String city, date, fuelType, engSize, emission, avgCo, mileYear;
        city = fromUserInterface.getStringExtra("City");
        date = fromUserInterface.getStringExtra("First Register Date");
        engSize = String.valueOf(fromUserInterface.getIntExtra("Engine Size", 2000));
        emission = String.valueOf(fromUserInterface.getIntExtra("Emission", 100));
        avgCo = String.valueOf(fromUserInterface.getDoubleExtra("Average Consume", 5));
        mileYear = String.valueOf(fromUserInterface.getIntExtra("Mileage Per Year", 1));
        fuelType = fromUserInterface.getStringExtra("Fuel Type");


        //Set Text To Text View
        txtViewUserMsgLast.setTextColor(Color.BLUE);
        txtViewUserMsgShowInput.setText(R.string.user_info_block);
        txtViewCityValue.setText(city);
        txtViewRegDateValue.setText(date);
        txtViewEngSizeValue.setText(engSize + " Liter");
        txtViewavgConeValue.setText(avgCo + " Liter per 100 km");
        txtViewMileAgeValue.setText(mileYear + " .000 km");
        txtViewFuelTypeValue.setText(fuelType);
        txtViewEmissionValue.setText(emission + " in cm2");
    }


    private void getProgressInfoBar() {

        progressBar.setVisibility(View.VISIBLE);
        setItemInvisible();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                setItemVisible();


            }
        };
        //Timer Tools
        // After 5 Second get object
        int interval = 3000;
        handler.postDelayed(runnable, interval);
    }

    private void setItemVisible() {

        txtViewUserMsgFirst.setVisibility(View.VISIBLE);
        txtViewFuelPrice.setVisibility(View.VISIBLE);
        txtViewAnnualTax.setVisibility(View.VISIBLE);
        txtViewFuelCost.setVisibility(View.VISIBLE);
        txtViewUserMsgLast.setVisibility(View.VISIBLE);
        txtViewUserMsgShowInput.setVisibility(View.VISIBLE);
        txtViewFuelCostValue.setVisibility(View.VISIBLE);
        txtViewFuelPriceValue.setVisibility(View.VISIBLE);
        txtViewAnnualValue.setVisibility(View.VISIBLE);
        txtViewCity.setVisibility(View.VISIBLE);
        txtViewRegDate.setVisibility(View.VISIBLE);
        txtViewEngSize.setVisibility(View.VISIBLE);
        txtViewAvgCon.setVisibility(View.VISIBLE);
        txtViewMileAge.setVisibility(View.VISIBLE);
        txtViewFuelType.setVisibility(View.VISIBLE);
        txtViewEmission.setVisibility(View.VISIBLE);
    }

    @Override
    public void showApiData(ApiDataModel apiDataModel) {

        if (apiDataModel == null) {
            taxApiError(apiDataModel.getError());

        } else {

            txtViewUserMsgFirst.setText(R.string.resutl_txt);
            String fuelPrice = String.valueOf(apiDataModel.getFuelPrice());
            txtViewFuelPriceValue.setText(fuelPrice + " Euro");
            String annualTax = String.valueOf(apiDataModel.getAnnualTax());
            txtViewAnnualValue.setText(annualTax + " Euro");
            String annualFuel = String.valueOf(apiDataModel.getAnnualFuelCosts());
            txtViewFuelCostValue.setText(annualFuel + " Euro");
            txtViewUserMsgLast.setTextColor(Color.BLUE);
            txtViewUserMsgLast.setText(R.string.help_ques);
            retryBtn.setVisibility(View.GONE);

        }
    }

    @Override
    public void taxApiError(String error) {
        Log.d("onFailuar", "Api Tax: " + error);
        Toast.makeText(getApplicationContext(),
                "onError fetchCarTaxInfo " + error,
                Toast.LENGTH_LONG).show();
        if (!TextUtils.isEmpty(error)) {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            txtViewUserMsgFirst.setText(R.string.no_res);
            txtViewUserMsgLast.setText(R.string.something_wrong);
            txtViewUserMsgLast.setTextColor(Color.RED);
            retryBtn.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // When ResultActivity start we call Controller
//        sendQueryToController();
    }

    private void sendQueryToController() {

        Intent fromUserInterface = getIntent();
        String city, date, fuelType;
        int engSizeIN, emissionIN, mileYearIN;
        double avgCoDO;
        city = fromUserInterface.getStringExtra("City");
        date = fromUserInterface.getStringExtra("First Register Date");
        fuelType = fromUserInterface.getStringExtra("Fuel Type");
        engSizeIN = fromUserInterface.getIntExtra("Engine Size", 1000);
        emissionIN = fromUserInterface.getIntExtra("Emission", 100);
        avgCoDO = fromUserInterface.getDoubleExtra("Average Consume", 6.0);
        mileYearIN = fromUserInterface.getIntExtra("Mileage Per Year", 10000);
        CarDetails carDetails = new CarDetails();
        carDetails.setAvgConsume(avgCoDO);
        carDetails.setCity("Bremen");
        carDetails.setEmission(emissionIN);
        carDetails.setEngineSize(engSizeIN);
        carDetails.setFuelType(fuelType);
        carDetails.setRegDate(date);
        carDetails.setYearlyMileage(mileYearIN);
        catTaxPresenter = new CatTaxPresenter(this, carDetails);
    }

    public void clickToRate() {

        RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .threshold(3)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                        Toast.makeText(getApplicationContext(),
                                "Thank you for your feedback", Toast.LENGTH_LONG).show();
                    }
                }).onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {

                    }
                })
                .build();

        ratingDialog.show();
    }
}

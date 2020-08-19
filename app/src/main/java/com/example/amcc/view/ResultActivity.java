package com.example.amcc.view;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.amcc.R;
import com.example.amcc.model.ApiDataModel;


public class ResultActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ResultActivity";
    private TextView txtViewUserMsgFirst, txtViewFuelPrice,
            txtViewAnnualTax, txtViewFuelCost, txtViewUserMsgLast,
            txtViewUserMsgShowInput, txtViewCity, txtViewRegDate,
            txtViewEngSize, txtViewFuelType, txtViewEmission;
    private Button newRequestBtn, editBtn;
    private boolean setNewRequest;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Initial Item
        initialItem();

        ApiDataModel object = getIntent().getParcelableExtra("ApiResponse");
        if (object != null) {
            Log.d(TAG, "onCreate: " + object.toString());
            try {

                String error = String.valueOf(object.getError());
                if (TextUtils.isEmpty(error)) {
                    String fuelPrice = String.valueOf(object.getFuelPrice());
                    String fuelCost = String.valueOf(object.getAnnualFuelCosts());
                    String annualTax = String.valueOf(object.getAnnualTax());
                    txtViewUserMsgFirst.setText(R.string.resutl_txt);
                    txtViewFuelPrice.setText(R.string.fuel_pice_res + fuelPrice);
                    Log.d("fuelPrice", fuelPrice);
                    txtViewFuelCost.setText(R.string.fuel_cost_annual + object.getAnnualFuelCosts());
                    Log.d("fuelCost", fuelCost);
                    txtViewAnnualTax.setText(R.string.annual_tax + object.getAnnualTax());
                    Log.d("annualTx", annualTax);
                } else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }

            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                showUserInput();
            }


        } else {
            txtViewUserMsgShowInput.setText(R.string.something_wrong);
            txtViewUserMsgShowInput.setTextColor(Color.RED);
            setItemInvisible();
        }
    }

    private void initialItem() {

        // TextView
        txtViewUserMsgFirst = findViewById(R.id.resultTxtViewUserMessageID);
        txtViewFuelPrice = findViewById(R.id.resultFuelPriceTxtViewID);
        txtViewFuelCost = findViewById(R.id.resultFuelCostTxtViewID);
        txtViewAnnualTax = findViewById(R.id.resultAnnualTaxTxtViewID);
        txtViewUserMsgLast = findViewById(R.id.resultUniversalTxtView);
        txtViewUserMsgShowInput = findViewById(R.id.resultTxtViewShowUserInputID);
        txtViewCity = findViewById(R.id.resultTxtViewCityID);
        txtViewRegDate = findViewById(R.id.resultTxtViewRegDateID);
        txtViewEngSize = findViewById(R.id.resultTxtViewEngSizeID);
        txtViewEmission = findViewById(R.id.resultTxtViewEmissionID);
        txtViewFuelType = findViewById(R.id.resultTxtViewFuelID);

        //Button
        newRequestBtn = findViewById(R.id.btnResultNewID);
        editBtn = findViewById(R.id.btnResultEditID);
        newRequestBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);

    }

    private void setItemInvisible() {

        txtViewUserMsgFirst.setVisibility(View.GONE);
        txtViewFuelPrice.setVisibility(View.GONE);
        txtViewAnnualTax.setVisibility(View.GONE);
        txtViewFuelCost.setVisibility(View.GONE);
        txtViewUserMsgLast.setVisibility(View.GONE);
        txtViewUserMsgShowInput.setVisibility(View.GONE);
        txtViewCity.setVisibility(View.GONE);
        txtViewRegDate.setVisibility(View.GONE);
        txtViewEngSize.setVisibility(View.GONE);
        txtViewFuelType.setVisibility(View.GONE);
        txtViewEmission.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnResultNewID:
                Intent userInterface = new Intent(ResultActivity.this, UserInterfaceActivity.class);
                setNewRequest = true;
                userInterface.putExtra("clearInstance", true);
                startActivity(userInterface);
                break;
            case R.id.btnResultEditID:
                break;
        }
    }

    private void showUserInput() {

        Intent fromUserInterface = getIntent();
        String city, date, fuelType, engSize, emission, avgCo, mileYear;
        city = fromUserInterface.getStringExtra("City");
        date = fromUserInterface.getStringExtra("First Register Date");
        engSize = fromUserInterface.getStringExtra("Engine Size");
        emission = fromUserInterface.getStringExtra("Emission");
        avgCo = fromUserInterface.getStringExtra("Average Consume");
        mileYear = fromUserInterface.getStringExtra("Mileage Per Year");
        fuelType = fromUserInterface.getStringExtra("Fuel Type");

        //Set Text To Text View
        txtViewUserMsgLast.setText(R.string.help_ques);
        txtViewUserMsgLast.setTextColor(Color.BLUE);
        txtViewUserMsgShowInput.setText(R.string.user_info_block);
        txtViewCity.setText(city);
        txtViewRegDate.setText(date);
        txtViewEngSize.setText(engSize);
        txtViewFuelType.setText(fuelType);
        txtViewEmission.setText(emission);
    }
}

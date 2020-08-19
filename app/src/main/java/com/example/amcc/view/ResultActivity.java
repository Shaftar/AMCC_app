package com.example.amcc.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Initial Item
        initialItem();

        ApiDataModel oject = getIntent().getParcelableExtra("ApiResponse");
        if (oject != null) {
            Log.d(TAG, "onCreate: " + oject.toString());
            txtViewUserMsgFirst.setText(R.string.resutl_txt);
            txtViewFuelPrice.setText(R.string.fuel_pice_res + (int) oject.getFuelPrice());
            txtViewFuelCost.setText(R.string.fuel_cost_annual + oject.getAnnualFuelCosts());
            txtViewAnnualTax.setText(R.string.annual_tax + oject.getAnnualTax());

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
                break;
            case R.id.btnResultEditID:
                break;
        }
    }

    private void showUserInput() {


    }
}

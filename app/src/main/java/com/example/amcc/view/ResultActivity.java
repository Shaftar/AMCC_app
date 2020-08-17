package com.example.amcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.amcc.R;
import com.example.amcc.model.ApiDataModel;
import com.example.amcc.util.ApiController;


public class ResultActivity extends BaseActivity {

    private static final String TAG = "ResultActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        ApiDataModel oject = intent.getParcelableExtra("apiModel");
        Log.d(TAG, "onCreate: " + oject.toString());
    }


}

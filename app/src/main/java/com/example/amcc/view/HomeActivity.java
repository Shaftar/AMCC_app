package com.example.amcc.view;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.amcc.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}

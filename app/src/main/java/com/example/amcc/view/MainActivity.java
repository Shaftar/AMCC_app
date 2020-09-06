package com.example.amcc.view;

import android.os.Bundle;

import com.example.amcc.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AdView adView = new AdView(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdView adView = findViewById(R.id.adView);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4242963717208905/1233368693");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

}
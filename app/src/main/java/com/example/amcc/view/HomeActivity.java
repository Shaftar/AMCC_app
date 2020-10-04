package com.example.amcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amcc.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        createToolBar();

        AppCenter.start(getApplication(), "abe64d09-d522-4ff8-9319-ac89d351c79a",
                Analytics.class, Crashes.class);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    public void shareIt() {
        //sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Thank you for sharing our App.";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share it");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, null));
    }
    public void createToolBar() {
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Trigger a menu item by setting item clickListener
        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.iHome:
                    NavController navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);
                    navController.navigate(R.id.homeFragment);
                    return true;
                case R.id.iShare:
                    shareIt();
                    return true;
                default:
                    return onOptionsItemSelected(item);
            }
        });
    }
}

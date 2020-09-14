package com.example.amcc.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.amcc.R;

public class BaseActivity extends AppCompatActivity {

    private WifiManager wifiManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    private void shareIt() {
        //sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Thank you for sharing our App.";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share it");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, null));
    }

    private void setupSetting() {
        // set up Settings activity
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void createToolBar() {

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Trigger a menu item by setting item clickListener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.iHome:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        return true;
                    case R.id.iShare:
                        shareIt();
                        return true;
                    case R.id.iSet:
                        setupSetting();
                        return true;
                    default:
                        return onOptionsItemSelected(item);
                }
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createToolBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public AlertDialog connectInternetDialog() {
        String msg = "Please check your internet connection" + '\n' +
                "Would you like to turn it on now?";
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Internet Connection");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isOnline() && Build.VERSION.SDK_INT < 29) {
                            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                        }
                        if (isOnline()) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            alertDialog.dismiss();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        alertDialog.show();
        return alertDialog;
    }
}

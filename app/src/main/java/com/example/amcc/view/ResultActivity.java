package com.example.amcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.amcc.R;


public class ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            //Inflate Menu
            getMenuInflater().inflate(R.menu.app_menu, menu);
            return true;
        }

    private void createToolBar() {

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Trigger a menu item by setting item clickListener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.iHome:
                        // Do something
                        Intent homeActivity = new Intent(ResultActivity.this, HomeActivity.class);
                        startActivity(homeActivity);
                        return true;
                    case R.id.iShare:
                        // Do something
                        shareIt();
                        return true;
                    case R.id.iSet:
                        // Do something
                        setupSetting();
                        return true;
                    default:
                        return ResultActivity.super.onOptionsItemSelected(item);
                }
            }
        });
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
        Intent settingsIntent = new Intent(ResultActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Create Menu
        createToolBar();
    }
}

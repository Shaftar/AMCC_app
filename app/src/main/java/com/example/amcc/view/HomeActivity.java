package com.example.amcc.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.amcc.R;
import com.example.amcc.adapter.CustomListAdapter;
import com.example.amcc.model.CarDetails;
import com.example.amcc.model.FunctionsList;
import com.example.amcc.mvp.ResultActivityForMVP;
import com.example.amcc.mvp.UserInterfaceActivityForMVP;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity {


    // References to feed our custom List Adapter object
//    int[] nameListArray = {R.string.the_first_function_name, R.string.the_second_function_name};
//    int[] infoListArray = {R.string.info_first_function, R.string.info_second_function};
//    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};
    private ArrayList<FunctionsList> functionsLists;
    private static final String DEBUG_TAG = "NetworkStatus";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = findViewById(R.id.progressBarHomeID);
        progressBar.setVisibility(View.GONE);

    }

    private void setListHomeAdapter() {

        functionsLists = new ArrayList<>();
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_first_function_name, R.string.info_first_function, R.drawable.car_ins));
        functionsLists.add(new FunctionsList(R.string.the_second_function_name, R.string.info_second_function, R.drawable.car_go));
        functionsLists.add(new FunctionsList(R.string.the_second_function_name, R.string.info_second_function, R.drawable.car_go));
        // Create Custom Adapter here
        CustomListAdapter homeListAdapter = new CustomListAdapter(this, functionsLists);
        // Link listView to our CustomListAdapter
        //Create list View
        ListView listView = findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);
        // Create EventListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent mainActivity = new Intent(HomeActivity.this, UserInterfaceActivityForMVP.class);
                        startActivity(mainActivity);
                        break;
                    case 1:
                        getCosts();
                        break;
                }
            }
        });
    }

    //For debugging purposes
    public void networkStatus() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn |= networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn |= networkInfo.isConnected();
                }

            } else {
                // not connected to the internet
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set List Adapter
        setListHomeAdapter();
    }

    public void getCosts() {
        networkStatus();
        progressBar.setVisibility(View.VISIBLE);
        Intent result = new Intent(HomeActivity.this, ResultActivityForMVP.class);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                startActivity(result);

            }
        };
        //Timer Tools
        // After 5 Second get object
        int interval = 5000;
        handler.postDelayed(runnable, interval);
    }
}

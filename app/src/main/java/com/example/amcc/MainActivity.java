package com.example.amcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import model.ApiDataModel;
import model.CarDetails;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import util.HttpHandling;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "NetworkStatus: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    // For check the Network Connection before
    // we request data
    public boolean checkNetwork() {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                return true;
            }
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }
    // Send Request to server and fetch the data
    private void createNetworking(RequestParams params) {
        if (checkNetwork()) {
            Log.d("App: CreateNetworking", "Network is working- Internet connected!");
            HttpHandling.get(HttpHandling.API_URL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("App: CreateNetworking", "OnSuccess- has response!");
                    ApiDataModel apiDataModel = ApiDataModel.fromJson(response);
                    CarDetails carDetails = CarDetails.fromJason(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    // If the response is JSONObject Failed
                    Log.d("App: onFailure", "---------------- No response! : " +
                            errorResponse.toString());
                    Toast.makeText(MainActivity.this,
                            "Request Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Values input user edit activity
    private void getFromUserEdit(int regYear, int engineSize, int co2, int fuelType) {

        RequestParams params = new RequestParams();
        params.put("", regYear);
        params.put("", engineSize);
        params.put("", co2);
        params.put("", fuelType);
        createNetworking(params);

    }

    //User Input values activity
    private void getFromUserInput(CarDetails carDetails) {

        RequestParams params = new RequestParams();
        params.put("", carDetails.getCity());
        params.put("", carDetails.getFirst_register_date());
        params.put("", carDetails.getEmission());
        params.put("", carDetails.getFuel_type());
        createNetworking(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
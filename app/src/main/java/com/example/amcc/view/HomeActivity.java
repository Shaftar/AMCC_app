package com.example.amcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amcc.R;
import com.example.amcc.adapter.CustomListAdapter;
import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.example.amcc.model.FuelType;
import com.example.amcc.retrofitApi.RetrofitClient;
import com.example.amcc.util.ApiController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends BaseActivity {


    // References to feed our custom List Adapter object
    String[] nameListArray = {"First Function", "Second Function"};
    String[] infoListArray = {"info about first function.", "info about second function."};
    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};
    //Create list View
    private ListView listView;
    private CustomListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    private void setListHomeAdapter() {

        // Create Custom Adapter here
        homeListAdapter = new CustomListAdapter(this, nameListArray, infoListArray, imgListArrayID);
        // Link listView to our CustomListAdapter
        listView = findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);
        // Create EventListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent mainActivity = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                        break;
                    case 1:
                        getCosts();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set List Adapter
        setListHomeAdapter();
    }

    public void getCosts() {

        //Create Api controller to fetch data
        ApiController controller = new ApiController(this);
    }
}

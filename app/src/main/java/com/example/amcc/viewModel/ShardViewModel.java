package com.example.amcc.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.example.amcc.retrofitApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShardViewModel extends ViewModel {
    private static final String TAG = "ShardViewModel";
    MutableLiveData<CarDetails> mCar = new MutableLiveData<>();
    MutableLiveData<ApiDataModel> mApiData = new MutableLiveData<>();


    public LiveData<ApiDataModel> getApiData() {
        return mApiData;
    }

    public void setApiData(CarDetails car) {
        RetrofitClient client = RetrofitClient.getINSTANCE();
        client.getCosts(car).enqueue(new Callback<ApiDataModel>() {
            @Override
            public void onResponse(Call<ApiDataModel> call, Response<ApiDataModel> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "setApiData: received response" + response.body().toString());
                    mApiData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<ApiDataModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}

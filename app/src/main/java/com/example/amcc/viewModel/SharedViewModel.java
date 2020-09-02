package com.example.amcc.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.example.amcc.retrofitApi.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedViewModel extends ViewModel {
    private static final String TAG = "ShardViewModel";
    MutableLiveData<CarDetails> mCar = new MutableLiveData<>();
    MutableLiveData<ApiDataModel> mApiData = new MutableLiveData<>();
    RetrofitClient client = RetrofitClient.getINSTANCE();

    public LiveData<ApiDataModel> getApiData() {
        return mApiData;
    }

    public void setApiData(CarDetails car) {

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

    public void setCitiesArray() {
        client.getCities().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println("fuck you bitch:" + response.code());
                System.out.println("fuck you bitch:" + response.body());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
}

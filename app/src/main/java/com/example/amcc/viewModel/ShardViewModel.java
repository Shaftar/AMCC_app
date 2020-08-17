package com.example.amcc.viewModel;

import android.util.Log;

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

    public void setApiData(MutableLiveData<CarDetails> mCar) {

    }

    public ApiDataModel getApiData() {
        return mApiData.getValue();

    }

    public void setCar(CarDetails car) {
        mCar.setValue(car);
        Log.d(TAG, "setApiData: " + mCar.getValue().toString());

        RetrofitClient client = RetrofitClient.getINSTANCE();
        client.getCosts(this.mCar.getValue()).enqueue(new Callback<ApiDataModel>() {
            @Override
            public void onResponse(Call<ApiDataModel> call, Response<ApiDataModel> response) {
                Log.d(TAG, "setApiData: " + response.body().toString());
                mApiData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiDataModel> call, Throwable t) {

            }
        });
    }


    public CarDetails getCar() {
        return mCar.getValue();

    }

}

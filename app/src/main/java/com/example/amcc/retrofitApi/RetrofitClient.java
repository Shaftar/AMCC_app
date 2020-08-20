package com.example.amcc.retrofitApi;

import com.example.amcc.model.AmccApi;
import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient INSTANCE;
    private AmccApi amccApi;

    private RetrofitClient() {
        String BASE_Url = "https://jdroubi.heliohost.org/amcc_api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        amccApi = retrofit.create(AmccApi.class);
    }

    public static RetrofitClient getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new RetrofitClient();
        return INSTANCE;
    }


    public Single<ApiDataModel> getCosts(CarDetails car) {
        return amccApi.GetCosts(car.getCity(), car.getEngineSize(), car.getRegDate(),
                car.getEmission(), car.getFuelType(), car.getAvgConsume(), car.getYearlyMileage());
    }
}

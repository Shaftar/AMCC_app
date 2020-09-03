package com.example.amcc.retrofitApi;

import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient INSTANCE;
    private final String BASE_Url = "http://77.23.21.154/amcc_api/";


    private AmccApi amccApi;

    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        amccApi = retrofit.create(AmccApi.class);
    }

    public static RetrofitClient getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new RetrofitClient();
        return INSTANCE;
    }


    public Call<ApiDataModel> getCosts(CarDetails car) {
        return amccApi.GetCosts(car.getCity(), car.getEngineSize(), car.getRegDate(),
                car.getEmission(), car.getFuelType(), car.getAvgConsume(), car.getYearlyMileage());
    }

    public Call<List<String>> getCities() {
        return amccApi.GetAllCities();

    }
}

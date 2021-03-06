package com.example.amcc.retrofitApi;

import com.example.amcc.model.ApiDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AmccApi {
    @GET("costs.php")
    Call<ApiDataModel> GetCosts(
            @Query("city") String city,
            @Query("engine_size") int engineSize,
            @Query("reg_date") String regDate,
            @Query("emission") int emission,
            @Query("fuel_type") String fuelType,
            @Query("avg_consume") double avgConsume,
            @Query("yearly_mileage") int yearlyMileage
    );

    @GET("api/city/read.php")
    Call<List<String>> GetAllCities();

    @GET("api/city/read_zip.php")
    Call<List<String>> GetAllPostCodes();
}

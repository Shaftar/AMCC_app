package com.example.amcc.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CountriesApi {

    @GET("all")
    Single<List<Country>> getCountries();
}

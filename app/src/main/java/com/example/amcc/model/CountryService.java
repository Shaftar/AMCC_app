package com.example.amcc.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryService {

    public static final String BASE_URL = "https://restcountries.eu/rest/v2/";
    private CountriesApi countriesApi;

    public CountryService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        countriesApi = retrofit.create(CountriesApi.class);
    }

    public Single<List<Country>> getCountries() {
        return countriesApi.getCountries();
    }
}

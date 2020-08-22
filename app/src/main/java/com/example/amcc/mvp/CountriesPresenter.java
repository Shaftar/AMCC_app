package com.example.amcc.mvp;

import android.view.View;

import com.example.amcc.model.Country;
import com.example.amcc.model.CountryService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesPresenter {

    //MVP Architecture doesn't know about which Activity works with!
    private ViewPresenter view;
    private CountryService countryService;

    public CountriesPresenter(ViewPresenter view) {
        this.view = view;
        countryService = new CountryService();
        fetchCountries();

    }

    private void fetchCountries() {
        countryService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(@NonNull List<Country> countries) {
                        List<String> countryName = new ArrayList<>();
                        for (Country country : countries) {
                            countryName.add(country.countryName);
                        }
                        view.setValues(countryName);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onError(e.getMessage());
                    }
                });
    }

    public void onRefresh() {
        fetchCountries();
    }

    public interface ViewPresenter {
        void setValues(List<String> countries);

        void onError(String errorMsg);
    }
}

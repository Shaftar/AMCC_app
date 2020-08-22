//package com.example.amcc.mvc;
//
//
//import android.widget.Toast;
//
//import com.example.amcc.model.Country;
//import com.example.amcc.model.CountryService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.annotations.NonNull;
//import io.reactivex.rxjava3.observers.DisposableSingleObserver;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//
//public class CountriesController {
//
//    private UserInterfaceActivity userInterfaceActivity;
//    private CountryService countryService;
//
//    public CountriesController(UserInterfaceActivity userInterfaceActivity) {
//        this.userInterfaceActivity = userInterfaceActivity;
//        this.countryService = new CountryService();
//        fetchCoutries();
//    }
//
//    private void fetchCoutries() {
//        countryService.getCountries()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<Country> countries) {
//                        List<String> countryName = new ArrayList<>();
//                        for (Country country : countries) {
//                            countryName.add(country.countryName);
//                        }
//                        userInterfaceActivity.setCountriesValues(countryName);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        userInterfaceActivity.countryApiError(e.getMessage());
//                    }
//                });
//    }
//}

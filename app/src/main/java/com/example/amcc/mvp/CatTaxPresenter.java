package com.example.amcc.mvp;


import android.util.Log;

import com.example.amcc.model.ApiDataModel;
import com.example.amcc.model.CarDetails;
import com.example.amcc.retrofitApi.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CatTaxPresenter {

    private static final String TAG = "Debug";
    private ViewTaxPresenter view;
    private CarDetails carDetails;

    public CatTaxPresenter(ViewTaxPresenter view, CarDetails car) {
        this.view = view;
        this.carDetails = car;
        fetchApiData(car);
    }


    private void fetchApiData(CarDetails car) {

        RetrofitClient client = RetrofitClient.getINSTANCE();
        client.getCosts(car).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ApiDataModel>() {
                    @Override
                    public void onSuccess(@NonNull ApiDataModel apiDataModel) {
                        view.showApiData(apiDataModel);
                        Log.d("onSuccess: ", apiDataModel.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.taxApiError(e.getMessage());
                        Log.d("onFailure: ", e.getMessage());
                    }
                });
    }

    public void onRefresh() {
        fetchApiData(carDetails);
    }

    public interface ViewTaxPresenter {
        void showApiData(ApiDataModel apiDataModel);

        void taxApiError(String errorMsg);
    }

}

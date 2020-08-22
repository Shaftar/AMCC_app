//package com.example.amcc.mvc;
//
//
//import android.util.Log;
//
//import com.example.amcc.model.ApiDataModel;
//import com.example.amcc.model.CarDetails;
//import com.example.amcc.retrofitApi.RetrofitClient;
//
//import java.util.Objects;
//
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.annotations.NonNull;
//import io.reactivex.rxjava3.observers.DisposableSingleObserver;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RetrofitApiController {
//
//    private static final String TAG = "Debug";
//    private ResultActivity activity;
//    private CarDetails carDetails;
//
//    public RetrofitApiController(ResultActivity resultActivity, CarDetails car) {
//        this.activity = resultActivity;
//        this.carDetails = car;
//        fetchApiData(car);
//    }
//
//
//    private void fetchApiData(CarDetails car) {
//
//        RetrofitClient client = RetrofitClient.getINSTANCE();
//        client.getCosts(car).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<ApiDataModel>() {
//                    @Override
//                    public void onSuccess(@NonNull ApiDataModel apiDataModel) {
//                        activity.showApiData(apiDataModel);
//                        Log.d("onSuccess: ", apiDataModel.toString());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        activity.taxApiError(e.getMessage());
//                        Log.d("onFailure: ", e.getMessage());
//                    }
//                });
//    }
//
//    public void onRefresh() {
//        fetchApiData(carDetails);
//    }
//
//}

package com.example.amcc.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.viewModel.SharedViewModel;

public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";
    SharedViewModel viewModel;
    TextView tax, fuelCosts, fuelPrice, regDate, fuelType, avgConsume, yearlyKilometer, city, emission, engineSize, rateAppDialog;
    private ProgressBar pgsBar;
    CarDetails car;

    private Button retryBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            car = bundle.getParcelable("carInformation");

        findViews(view);

        pgsBar.setVisibility(View.VISIBLE);
        retryBtn.setOnClickListener(view1 -> {
            pgsBar.setVisibility(View.VISIBLE);
            viewModel.setApiData(car);
            retryBtn.setVisibility(View.GONE);
        });
        retryBtn.setVisibility(View.GONE);

        Button editBtn = view.findViewById(R.id.edit_btn_id);
        editBtn.setOnClickListener(view12 -> requireActivity().onBackPressed());

        setValues(car);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        fetchApiData();
        rateAppDialog.setOnClickListener(view13 -> {
            RatingDialog ratingDialog = new RatingDialog.Builder(requireActivity())
                    .threshold(3)
                    .onRatingBarFormSumbit(feedback -> Toast.makeText(getActivity(),
                            "Thank you for your feedback", Toast.LENGTH_LONG).show()).onRatingChanged((rating, thresholdCleared) -> {
                    })
                    .build();
            ratingDialog.show();
        });

    }

    private void findViews(@NonNull View view) {
        regDate = view.findViewById(R.id.resultRegDateValueID);
        fuelType = view.findViewById(R.id.resultFuelTypeValueID);
        avgConsume = view.findViewById(R.id.resultAvgConValueID);
        yearlyKilometer = view.findViewById(R.id.resultKilometerYearValueID);
        emission = view.findViewById(R.id.resultEmissionValueID);
        city = view.findViewById(R.id.resultCityValueID);
        engineSize = view.findViewById(R.id.resultEngSizeValueID);
        rateAppDialog = view.findViewById(R.id.rate_app_dialog_id);
        tax = view.findViewById(R.id.resultAnnualValueID);
        fuelCosts = view.findViewById(R.id.resultFuelCostValueID);
        fuelPrice = view.findViewById(R.id.resultFuelPriceValueID);
        pgsBar = view.findViewById(R.id.progressBarResultInfoID);
        retryBtn = view.findViewById(R.id.retry_btn_id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setValues(CarDetails car) {
        regDate.setText(car.getRegDate());
        fuelType.setText(String.valueOf(car.getFuelType()));
        avgConsume.setText(String.valueOf(car.getAvgConsume()));
        yearlyKilometer.setText(String.valueOf(car.getYearlyMileage()));
        if(car.getEmission()==0){
            requireView().findViewById(R.id.emission_label).setVisibility(View.GONE);
            emission.setVisibility(View.GONE);
        }
        emission.setText(String.valueOf(car.getEmission()));
        city.setText(car.getCity());
        engineSize.setText(String.valueOf(car.getEngineSize()));
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void fetchApiData() {
        viewModel.getApiData().observe(getViewLifecycleOwner(), apiDataModel -> {
            pgsBar.setVisibility(View.GONE);
            rateAppDialog.setText(R.string.dialog_rate_app);
            rateAppDialog.setTextColor(Color.BLUE);
            tax.setText(String.valueOf(apiDataModel.getAnnualTax()));
            fuelPrice.setText(String.valueOf(apiDataModel.getFuelPrice()));
            fuelCosts.setText(String.valueOf(apiDataModel.getAnnualFuelCosts()));
        });
        viewModel.getTaxError().observe(getViewLifecycleOwner(), error -> {
            if (error) {
                retryBtn.setVisibility(View.VISIBLE);
                pgsBar.setVisibility(View.GONE);
                rateAppDialog.setText(R.string.try_later_msg);
                rateAppDialog.setTextColor(Color.RED);
            }
        });
    }

}
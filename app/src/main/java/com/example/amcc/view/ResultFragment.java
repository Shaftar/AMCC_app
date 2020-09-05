package com.example.amcc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.viewModel.SharedViewModel;

public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";
    SharedViewModel viewModel;
    TextView tax, fuelCosts, fuelPrice, regDate, fuelType, avgConsume, yearlyKilometer, city, emission, engineSize;
    private ProgressBar pgsBar;
    CarDetails car;

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
        regDate = view.findViewById(R.id.resultRegDateValueID);
        fuelType = view.findViewById(R.id.resultFuelTypeValueID);
        avgConsume = view.findViewById(R.id.resultAvgConValueID);
        yearlyKilometer = view.findViewById(R.id.resultKilometerYearValueID);
        emission = view.findViewById(R.id.resultEmissionValueID);
        city = view.findViewById(R.id.resultCityValueID);
        engineSize = view.findViewById(R.id.resultEngSizeValueID);

        pgsBar = view.findViewById(R.id.progressBarResultInfoID);
        pgsBar.setVisibility(View.VISIBLE);


        tax = view.findViewById(R.id.resultAnnualValueID);
        fuelCosts = view.findViewById(R.id.resultFuelCostValueID);
        fuelPrice = view.findViewById(R.id.resultFuelPriceValueID);
        setValues(car);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        ApiDataModel result = viewModel.getApiData().getValue();
        viewModel.getApiData().observe(getViewLifecycleOwner(), apiDataModel -> {
            pgsBar.setVisibility(View.GONE);
            tax.setText(String.valueOf(apiDataModel.getAnnualTax()));
            fuelPrice.setText(String.valueOf(apiDataModel.getFuelPrice()));
            fuelCosts.setText(String.valueOf(apiDataModel.getAnnualFuelCosts()));
        });
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
        emission.setText(String.valueOf(car.getEmission()));
        city.setText(car.getCity());
        engineSize.setText(String.valueOf(car.getEngineSize()));
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}
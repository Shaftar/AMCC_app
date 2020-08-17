package com.example.amcc.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.model.FuelType;
import com.example.amcc.viewModel.ShardViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private static final String TAG = "PrintDate";
    private Spinner spinner;
    private EditText emissionEdtField, engineSizeField, avgConField, milePerYField;
    private String citySelected, regDate;
    SimpleDateFormat germanFormat;
    CalendarView calendarView;

    NavController navController;

    ShardViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ShardViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinnerCityHolderID);
        emissionEdtField = view.findViewById(R.id.edtNumEmissionID);
        engineSizeField = view.findViewById(R.id.edtNumEngSizeID);
        avgConField = view.findViewById(R.id.edtNumConsumeID);
        milePerYField = view.findViewById(R.id.edtNumMileAgeYearID);
        calendarView = view.findViewById(R.id.calendarViewID);
        germanFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        regDate = germanFormat.format(calendarView.getDate());
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> regDate = "" + dayOfMonth + "." + (month + 1) + "." + year);
        //Create City Name List
        setUpCityNamesList();

        navController = Navigation.findNavController(view);
        view.findViewById(R.id.btnResultID).setOnClickListener(v -> {
            CarDetails car = new CarDetails(citySelected, Integer.parseInt(engineSizeField.getText().toString()), Integer.parseInt(emissionEdtField.getText().toString()), FuelType.e5, regDate, Integer.parseInt(avgConField.getText().toString()), Integer.parseInt(milePerYField.getText().toString()));
            viewModel.setApiData(car);
            navController.navigate(R.id.resultFragment);
        });
    }

    private void setUpCityNamesList() {

        List<String> cityNameArray = new ArrayList<>();
        cityNameArray.add("Bremen");
        cityNameArray.add("Berlin");
        cityNameArray.add("Hamburg");
        cityNameArray.add("Bayern");
        // City List tools
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, cityNameArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySelected = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getResult(View view) {

        // Check user Input and get result
        checkUserInput();
    }

    private void checkUserInput() {

        // Reset errors displayed in the form.
        emissionEdtField.setError(null);
        engineSizeField.setError(null);
        avgConField.setError(null);
        milePerYField.setError(null);

        // Store values at the time of the entered vlaues
        String emission = emissionEdtField.getText().toString();
        String engSize = engineSizeField.getText().toString();
        String avgCon = avgConField.getText().toString();
        String milePerY = milePerYField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(emission)) {
            emissionEdtField.setError(getString(R.string.error_field_required));
            focusView = emissionEdtField;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(milePerY)) {
            milePerYField.setError(getString(R.string.error_field_required));
            focusView = milePerYField;
            cancel = true;
        }
        if (TextUtils.isEmpty(engSize)) {
            engineSizeField.setError(getString(R.string.error_field_required));
            focusView = engineSizeField;
            cancel = true;
        }
        if (TextUtils.isEmpty(avgCon)) {
            avgConField.setError(getString(R.string.error_field_required));
            focusView = avgConField;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't get Result and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
    }


    // For check the Network Connection before
    // we request data
//    public boolean checkNetwork() {
//        try {
//            ConnectivityManager connectivityManager =
//                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                //we are connected to a network
//                return true;
//            }
//        } catch (Exception e) {
//            Log.e("Connectivity Exception", e.getMessage());
//        }
//        return false;
//    }


}
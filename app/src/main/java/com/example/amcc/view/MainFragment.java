package com.example.amcc.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.model.FuelType;
import com.example.amcc.viewModel.SharedViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private static final String TAG = "PrintDate";
    private Spinner spinner;
    private EditText emissionEdtField, engineSizeField, avgConField, milePerYField, regDateField;
    private String citySelected, regDate;
    RadioGroup radioGroup;
    RadioButton radioButton;
    GridLayout emissionGird;

    AwesomeValidation validation;
    NavController navController;

    SharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }

    private TextWatcher showHideEmission = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                SimpleDateFormat geFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                Date d1 = geFormat.parse(regDateField.getText().toString());
                Date fixDate = geFormat.parse("5.11.2008");
                if (d1.compareTo(fixDate) < 0) {
                    emissionGird.setVisibility(View.GONE);
                    return;
                }
                emissionGird.setVisibility(View.VISIBLE);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        regDateField = view.findViewById(R.id.edtFirst_reg_year);
        emissionGird = view.findViewById(R.id.emission_layout);

        regDateField.addTextChangedListener(showHideEmission);

        navController = Navigation.findNavController(view);
        validation = new AwesomeValidation(ValidationStyle.BASIC);
        //Create City Name List
        setUpCityNamesList(view);

        view.findViewById(R.id.btnResultID).setOnClickListener(v -> {

            FuelType fuelType = getFuelType(view);

            engineSizeField = view.findViewById(R.id.edtNumEngSizeID);
            avgConField = view.findViewById(R.id.edtNumConsumeID);
            milePerYField = view.findViewById(R.id.edtNumMileAgeYearID);
            regDateField = view.findViewById(R.id.edtFirst_reg_year);
            emissionEdtField = view.findViewById(R.id.edtNumEmissionID);

            if (emissionGird.getVisibility() == View.GONE) {
                emissionEdtField.setText("0");
            }
            validateInput();


            if (validation.validate()) {
                CarDetails car = new CarDetails(citySelected,
                        Integer.parseInt(engineSizeField.getText().toString()),
                        Integer.parseInt(emissionEdtField.getText().toString()),
                        fuelType,
                        regDateField.getText().toString(),
                        Double.parseDouble(avgConField.getText().toString()),
                        Integer.parseInt(milePerYField.getText().toString()));

                viewModel.setApiData(car);
                navController.navigate(R.id.resultFragment);
            }
        });
    }

    private FuelType getFuelType(@NonNull View view) {
        radioGroup = view.findViewById(R.id.fuel_type_radio_group);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
        String fuelType = radioButton.getText().toString();
        return FuelType.fromString(fuelType);
    }

    private void validateInput() {
        String errorMsg = "field should not be empty";
        validation.addValidation(regDateField, "(0?[1-9]|[1-2]\\d|30|31).(0?[1-9]|1[0-2]).(\\d{4})", "Empty or invalid");
        validation.addValidation(emissionEdtField, RegexTemplate.NOT_EMPTY, getString(R.string.error_field_required));
        validation.addValidation(engineSizeField, "[0-9]+", errorMsg);
        validation.addValidation(milePerYField, RegexTemplate.NOT_EMPTY, errorMsg);
        validation.addValidation(avgConField, RegexTemplate.NOT_EMPTY, errorMsg);
    }

    private void setUpCityNamesList(View view) {
        spinner = view.findViewById(R.id.spinnerCityHolderID);

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
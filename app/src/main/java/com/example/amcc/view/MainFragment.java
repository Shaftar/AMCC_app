package com.example.amcc.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.amcc.R;
import com.example.amcc.model.CarDetails;
import com.example.amcc.viewModel.SharedViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends BaseFragment {

    private EditText emissionEdtField, engineSizeField, avgConField, milePerYField, regDateField;
    String dateFormUser;
    RadioGroup radioGroup;
    RadioButton radioButton;
    GridLayout emissionGird, emissionKlasse;
    AutoCompleteTextView city;
    private DatePicker calendarView;
    AwesomeValidation validation;
    NavController navController;

    SharedViewModel viewModel;
    public InterstitialAd mInterstitialAd;

    List<String> cityFromApi = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInterstitialAd = new InterstitialAd(requireActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        emissionKlasse = view.findViewById(R.id.emission_class_layout);
        regDateField = view.findViewById(R.id.edtFirst_reg_year);
        emissionGird = view.findViewById(R.id.emission_layout);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getCities().observe(getViewLifecycleOwner(), cities -> {
            cityFromApi.addAll(cities);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                    android.R.layout.simple_dropdown_item_1line, cities);
            city = view.findViewById(R.id.city_list);
            city.setAdapter(adapter);

        });
        regDateField.addTextChangedListener(showHideEmission);

        navController = Navigation.findNavController(view);
        validation = new AwesomeValidation(ValidationStyle.BASIC);
        //Create City Name List
        onClickRegDate(view);

        view.findViewById(R.id.btnResultID).setOnClickListener(v -> {
            if (!isOnline()) {
                connectInternetDialog();
            } else {
                String fuelType = getFuelType(view);

                city = view.findViewById(R.id.city_list);
                engineSizeField = view.findViewById(R.id.edtNumEngSizeID);
                avgConField = view.findViewById(R.id.edtNumConsumeID);
                milePerYField = view.findViewById(R.id.edtNumMileAgeYearID);
                regDateField = view.findViewById(R.id.edtFirst_reg_year);
                emissionEdtField = view.findViewById(R.id.edtNumEmissionID);
                if (inputIsValid()) {
                    CarDetails car = new CarDetails(city.getText().toString(),
                            Integer.parseInt(engineSizeField.getText().toString()),
                            fuelType,
                            regDateField.getText().toString(),
                            Double.parseDouble(avgConField.getText().toString()),
                            Integer.parseInt(milePerYField.getText().toString()));

                    if (emissionGird.getVisibility() == View.VISIBLE) {
                        car.setEmission(Integer.parseInt(emissionEdtField.getText().toString()));
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("carInformation", car);
                    mInterstitialAd.show();
                    viewModel.setApiData(car);
                    navController.navigate(R.id.resultFragment, bundle);
                }
            }
        });
        }


    private String getFuelType(@NonNull View view) {
        radioGroup = view.findViewById(R.id.fuel_type_radio_group);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
        return radioButton.getText().toString();
    }

    private TextWatcher showHideEmission = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                SimpleDateFormat geFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                Date regDateOfCar = geFormat.parse(regDateField.getText().toString());
                Date oldRegulation = geFormat.parse("5.11.2008");
                Date newRegulation = geFormat.parse("01.07.2009");

                assert regDateOfCar != null;
                if (regDateOfCar.after(oldRegulation) && regDateOfCar.before(newRegulation)) {
                    emissionGird.setVisibility(View.VISIBLE);
                    emissionKlasse.setVisibility(View.VISIBLE);
                    return;
                }

                if (regDateOfCar.before(oldRegulation)) {
                    emissionGird.setVisibility(View.GONE);
                    emissionKlasse.setVisibility(View.VISIBLE);
                    return;
                }

                if (regDateOfCar.before(newRegulation)) {
                    emissionKlasse.setVisibility(View.VISIBLE);
                    return;
                }
                emissionKlasse.setVisibility(View.GONE);
                emissionGird.setVisibility(View.VISIBLE);


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private boolean inputIsValid() {
        String errorMsg = "field should not be empty";

        validation.addValidation(city, RegexTemplate.NOT_EMPTY, getString(R.string.error_field_required));
        validation.addValidation(regDateField, RegexTemplate.NOT_EMPTY, errorMsg);
        if (emissionGird.getVisibility() == View.VISIBLE)
            validation.addValidation(emissionEdtField, "([1-9]\\d\\d?)", getString(R.string.error_field_required));
        validation.addValidation(engineSizeField, "([1-9]\\d{2}\\d?)", errorMsg);

        validation.addValidation(milePerYField, "([1-9]\\d?\\d?)", errorMsg);

        validation.addValidation(avgConField, "(^[1-9]\\d?(\\.[0-9]\\d?)?$)", errorMsg);
        View error = city;
        String location = city.getText().toString();
        if (!cityFromApi.contains(location) && !viewModel.mPostCodes.contains(location)) {
            error.requestFocus();
            city.setError("city/postcode not found!");
            validation.validate();
            return false;
        }
        error.clearFocus();
        city.setError(null);
        return validation.validate();
    }


    private void onClickRegDate(View view) {
        regDateField.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireActivity());
            dialog.setContentView(R.layout.calender_view);
            calendarView = dialog.findViewById(R.id.calendarViewID);
            Button okBtn = dialog.findViewById(R.id.okCalenderBtnID);
            Button cancelBtn = dialog.findViewById(R.id.cancelCalenderBtnID);
            cancelBtn.setOnClickListener(view1 -> dialog.dismiss());

            String dateFromInput = regDateField.getText().toString();

            // should open on from text edit if not empty
            if (Build.VERSION.SDK_INT > 25) {

                // Ok button of dialog
                // if no date set yet
                // set text field to date of today
                okBtn.setOnClickListener(view12 -> {
                    if (dateFromInput.isEmpty()) {
                        LocalDate now = LocalDate.now();
                        String date = now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        regDateField.setText(date);
                    }
                    regDateField.setError(null);
                    dialog.dismiss();
                });

                //If Reg date was not empty then the date picker will start from the entered date
                if (!dateFromInput.isEmpty()) {
                    int[] date = dateArray(dateFromInput);
                    calendarView.updateDate(date[0], date[1], date[2]);
                }

                calendarView.setMaxDate(System.currentTimeMillis());
                calendarView.setOnDateChangedListener((view13, year, month, dayOfMonth) -> okBtn.setOnClickListener(view14 -> {

                    formatDate(year, month, dayOfMonth);
                    regDateField.setText(dateFormUser);
                    dialog.dismiss();
                }));
                dialog.show();
                dialog.setCancelable(true);
            } else {
                Calendar calendarOld = Calendar.getInstance();
                int year = calendarOld.get(Calendar.YEAR);
                int month = calendarOld.get(Calendar.MONTH);
                int day = calendarOld.get(Calendar.DAY_OF_MONTH);

                if (!dateFromInput.isEmpty()) {
                    int[] date = dateArray(dateFromInput);
                    year = date[0];
                    month = date[1];
                    day = date[2];
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), (view13, year1, month1, dayOfMonth) -> {
                    formatDate(year1, month1, dayOfMonth);
                    regDateField.setText(dateFormUser);
                    regDateField.setError(null);
                }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    private void formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        dateFormUser = format.format(calendar.getTime());
    }

    private int[] dateArray(String date) {
        String[] parts = date.split("\\.");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return new int[]{year, month - 1, day};
    }
}

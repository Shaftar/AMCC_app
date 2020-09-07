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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private EditText emissionEdtField, engineSizeField, avgConField, milePerYField, regDateField;
    String dateFormUser;
    RadioGroup radioGroup;
    RadioButton radioButton;
    GridLayout emissionGird;
    AutoCompleteTextView city;
    TextView emissionKlasse;
    private DatePicker calendarView;
    AwesomeValidation validation;
    NavController navController;

    SharedViewModel viewModel;
    public InterstitialAd mInterstitialAd;

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

    private TextWatcher showHideEmission = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                SimpleDateFormat geFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                Date regDateOfCar = geFormat.parse(regDateField.getText().toString());
                Date oldRegulation = geFormat.parse("5.11.2008");
                Date newRegulation = geFormat.parse("01.07.2009");

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

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        emissionKlasse = view.findViewById(R.id.txtView_euro3);
        regDateField = view.findViewById(R.id.edtFirst_reg_year);
        emissionGird = view.findViewById(R.id.emission_layout);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getCities().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> cities) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, cities);
                city = view.findViewById(R.id.city_list);
                city.setAdapter(adapter);

            }
        });
        regDateField.addTextChangedListener(showHideEmission);

        navController = Navigation.findNavController(view);
        validation = new AwesomeValidation(ValidationStyle.BASIC);
        //Create City Name List
        onClickCalenderImage(view);

        view.findViewById(R.id.btnResultID).setOnClickListener(v -> {

            String fuelType = getFuelType(view);

            engineSizeField = view.findViewById(R.id.edtNumEngSizeID);
            avgConField = view.findViewById(R.id.edtNumConsumeID);
            milePerYField = view.findViewById(R.id.edtNumMileAgeYearID);
            regDateField = view.findViewById(R.id.edtFirst_reg_year);
            emissionEdtField = view.findViewById(R.id.edtNumEmissionID);


            if (emissionGird.getVisibility() == View.GONE) {
                emissionEdtField.setText("0");
            }

            if (inputIsValid()) {
                CarDetails car = new CarDetails(city.getText().toString(),
                        Integer.parseInt(engineSizeField.getText().toString()),
                        Integer.parseInt(emissionEdtField.getText().toString()),
                        fuelType,
                        regDateField.getText().toString(),
                        Double.parseDouble(avgConField.getText().toString()),
                        Integer.parseInt(milePerYField.getText().toString()));
                Bundle bundle = new Bundle();
                bundle.putParcelable("carInformation", car);
                mInterstitialAd.show();
                viewModel.setApiData(car);
                navController.navigate(R.id.resultFragment, bundle);
            }
        });
    }


    private String getFuelType(@NonNull View view) {
        radioGroup = view.findViewById(R.id.fuel_type_radio_group);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
        return radioButton.getText().toString();
    }

    private boolean inputIsValid() {
        String errorMsg = "field should not be empty";
        validation.addValidation(regDateField, "(0?[1-9]|[1-2]\\d|30|31).(0?[1-9]|1[0-2]).(\\d{4})", "Empty or invalid");
        validation.addValidation(emissionEdtField, RegexTemplate.NOT_EMPTY, getString(R.string.error_field_required));
        validation.addValidation(engineSizeField, "[0-9]+", errorMsg);
        validation.addValidation(milePerYField, RegexTemplate.NOT_EMPTY, errorMsg);
        validation.addValidation(avgConField, RegexTemplate.NOT_EMPTY, errorMsg);
        return validation.validate();
    }

    private void onClickCalenderImage(View view) {
        ImageView dateImageView = view.findViewById(R.id.calender_image);
        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.calender_view);
                calendarView = dialog.findViewById(R.id.calendarViewID);
                Button okBtn = dialog.findViewById(R.id.okCalenderBtnID);
                Button cancelBtn = dialog.findViewById(R.id.cancelCalenderBtnID);
                cancelBtn.setOnClickListener(view1 -> dialog.dismiss());

                if (Build.VERSION.SDK_INT > 25) {
                    calendarView.setMaxDate(System.currentTimeMillis());
                    calendarView.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            okBtn.setOnClickListener(view12 -> {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
                                dateFormUser = format.format(calendar.getTime());
                                regDateField.setText(dateFormUser);
                                dialog.dismiss();
                            });
                        }
                    });
                    dialog.show();
                    dialog.setCancelable(true);
                } else {
                    Calendar calendarOld = Calendar.getInstance();
                    int year = calendarOld.get(Calendar.YEAR);
                    int month = calendarOld.get(Calendar.MONTH);
                    int day = calendarOld.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
                            dateFormUser = format.format(calendar.getTime());
                            regDateField.setText(dateFormUser);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }
        });
    }
}
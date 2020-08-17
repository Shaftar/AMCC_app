package com.example.amcc.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.amcc.R;
import com.example.amcc.model.ApiDataModel;
import com.example.amcc.viewModel.ShardViewModel;

public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";
    ShardViewModel viewModel;
    TextView tax;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tax = view.findViewById(R.id.tax_cost_textview);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ShardViewModel.class);
        viewModel.getApiData().observe(getViewLifecycleOwner(), new Observer<ApiDataModel>() {
            @Override
            public void onChanged(ApiDataModel apiDataModel) {
                Log.d(TAG, "ShardViewModel: " + apiDataModel.toString());
                tax.setText(apiDataModel.toString());
            }
        });
    }
}
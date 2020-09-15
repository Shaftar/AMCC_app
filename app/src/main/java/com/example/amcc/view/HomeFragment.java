package com.example.amcc.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amcc.R;
import com.example.amcc.adapter.CustomListAdapter;


public class HomeFragment extends BaseFragment{

    String[] nameListArray = {"First Function", "Second Function"};
    String[] infoListArray = {"info about first function.", "info about second function."};
    Integer[] imgListArrayID = {R.drawable.car_ins, R.drawable.car_go};
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListHomeAdapter(view);
        navController = Navigation.findNavController(view);
    }
    private void setListHomeAdapter(View view) {
        // Create Custom Adapter here
        CustomListAdapter homeListAdapter = new CustomListAdapter(requireActivity(), nameListArray, infoListArray, imgListArrayID);
        // Link listView to our CustomListAdapter
        //Create list View
        ListView listView = view.findViewById(R.id.homelistViewID);
        listView.setAdapter(homeListAdapter);
        // Create EventListener
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position) {
                case 0:
                    if (!isOnline()) {
                        connectInternetDialog();
                    } else {
                        navController.navigate(R.id.mainFragment);
                    }

                    break;
                case 1:
                    break;
            }
        });
    }
}
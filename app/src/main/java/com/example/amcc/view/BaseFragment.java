package com.example.amcc.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment{

    public BaseFragment() {}

    public AlertDialog connectInternetDialog() {
        String msg = "Please check your internet connection" + '\n' +
                "Would you like to turn it on now?";
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Internet Connection");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Try Again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        alertDialog.show();
        return alertDialog;
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}

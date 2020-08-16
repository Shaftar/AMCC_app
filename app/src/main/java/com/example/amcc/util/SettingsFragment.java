package com.example.amcc.util;



import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.amcc.R;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_setting, rootKey);
    }
}

package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.s15.campanilla.villanueva.playbach.R;

public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "SettingsPreferences";


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize switches
        Switch toggleSwitch1 = view.findViewById(R.id.toggleSwitch1);
        Switch toggleSwitch2 = view.findViewById(R.id.toggleSwitch2);
        Switch toggleSwitch3 = view.findViewById(R.id.toggleSwitch3);

        // Load preferences
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        toggleSwitch1.setChecked(preferences.getBoolean("toggleSwitch1", true));
        toggleSwitch2.setChecked(preferences.getBoolean("toggleSwitch2", true));
        toggleSwitch3.setChecked(preferences.getBoolean("toggleSwitch3", true));

        // Set listeners
        toggleSwitch1.setOnCheckedChangeListener((buttonView, isChecked) -> savePreference("toggleSwitch1", isChecked));
        toggleSwitch2.setOnCheckedChangeListener((buttonView, isChecked) -> savePreference("toggleSwitch2", isChecked));
        toggleSwitch3.setOnCheckedChangeListener((buttonView, isChecked) -> savePreference("toggleSwitch3", isChecked));
    }

    private void savePreference(String key, boolean value) {
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
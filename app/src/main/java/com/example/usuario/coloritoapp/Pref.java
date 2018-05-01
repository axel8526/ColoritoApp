package com.example.usuario.coloritoapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.usuario.coloritoapp.R;

/**
 * Created by Usuario on 20/04/2018.
 */

public class Pref extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}

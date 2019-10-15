package com.example.mainproject.UtilityClasses;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mainproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionTimedOut extends Fragment {


    public ConnectionTimedOut() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection_timed_out, container, false);
    }

}

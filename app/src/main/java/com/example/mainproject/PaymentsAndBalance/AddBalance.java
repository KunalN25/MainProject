package com.example.mainproject.PaymentsAndBalance;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBalance extends Fragment {


    public AddBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_balance, container, false);
    }

}

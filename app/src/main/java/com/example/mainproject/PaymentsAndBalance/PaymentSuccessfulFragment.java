package com.example.mainproject.PaymentsAndBalance;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentSuccessfulFragment extends Fragment {
    private String TAG="kun";
    private PaymentSuccessfullMethods paymentSuccessfullMethods;
    public PaymentSuccessfulFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_payment_successful, container, false);
        paymentSuccessfullMethods.setSuccessful(true);
        Log.d(TAG, "onCreateView: fragment is started");
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        paymentSuccessfullMethods=(PaymentSuccessfullMethods)context;
    }

    interface PaymentSuccessfullMethods{
        void setSuccessful(boolean success);
        void notifyOrderPlaced();
    }

}

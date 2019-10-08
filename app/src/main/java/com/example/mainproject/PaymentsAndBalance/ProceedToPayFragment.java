package com.example.mainproject.PaymentsAndBalance;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProceedToPayFragment extends Fragment implements View.OnClickListener {
    int totalPrice;
    private static final String TAG="kun";
    private TextView subTotalText,subTotalValue,gstText,gstValue,grandTotalText,grandTotalValue;
    double grandTotal;
    Button payButton,cancelButton;
    ProceedToPayFragmentMethods proceed;
    public ProceedToPayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_proceed_to_pay, container, false);
        initialize(v);

        setTextFieldValues();
        payButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        return v;
    }

    private void setTextFieldValues() {
        subTotalValue.setText("Rs."+totalPrice);
        double gst=0.15*totalPrice;
        gstValue.setText("Rs."+gst);
        grandTotal=(totalPrice+gst);
        grandTotalValue.setText("Rs."+grandTotal);
        payButton.setText("Proceed to pay Rs."+grandTotal);
    }

    private void initialize(View v) {
        subTotalText=v.findViewById(R.id.subTotal);
        subTotalValue=v.findViewById(R.id.subTotalValue);
        gstText=v.findViewById(R.id.gst);
        grandTotalText=v.findViewById(R.id.grandTotalText);
        gstValue=v.findViewById(R.id.gstValue);
        grandTotalValue=v.findViewById(R.id.grandTotalValue);
        payButton=v.findViewById(R.id.payButton);
        cancelButton=v.findViewById(R.id.cancelPaymentButton);

    }

    void getTotalPrice(int totalPrice) {
        this.totalPrice=totalPrice;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        proceed=(ProceedToPayFragmentMethods)context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.payButton:            proceed.proceedToPay(grandTotal);
                                            break;
            case R.id.cancelPaymentButton:  proceed.setPaymentCanceled(true);
                Log.d(TAG, "onClick: cancel is clicked ");
                                            break;
        }
    }
    interface ProceedToPayFragmentMethods{
        void proceedToPay(double grandTotal);
        void setPaymentCanceled(boolean cancel);
    }
}

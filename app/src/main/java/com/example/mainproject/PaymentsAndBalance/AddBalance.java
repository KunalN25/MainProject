package com.example.mainproject.PaymentsAndBalance;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBalance extends Fragment implements View.OnClickListener {
    private static ProgressBar progressBar;
    private EditText balanceIn;
    private Button addBtn;
    private double balance;
    static UpdateBalance updateBalance;
    static boolean listenToTouchEvents;
    public AddBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_balance, container, false);
        initialize(v);
        addBtn.setOnClickListener(this);

        return v;
    }

    private void initialize(View v) {
        balanceIn=v.findViewById(R.id.baclanceInPut);
        addBtn=v.findViewById(R.id.addBalanceButton);
        progressBar=v.findViewById(R.id.addBalanceProgress);
        listenToTouchEvents=false;
        balance=500;

        //balance=load balance from sharedpreferences
    }


    @Override
    public void onClick(View view) {
        double tempbal=Double.parseDouble(balanceIn.getText().toString());
        if(tempbal==0){
            Message.message(getActivity(),"Please enter the amount");
            return;
        }

        if(balance+tempbal>10000)
            Message.message(getActivity(),"You cannot add more than 10000");
        else{
            updateBalance=new UpdateBalance(getActivity());
            listenToTouchEvents=true;

            updateBalance.execute(balance,tempbal);

            try {

                balance=updateBalance.get();
            } catch (ExecutionException e) {
                Log.d("tag", "onClick: "+e.toString());
            } catch (InterruptedException e) {
                Log.d("tag", "onClick: "+e.toString());
            }

        }

    }

    private void updateBalance() {
    }
     static class UpdateBalance extends AsyncTask<Double,Void,Double>{
        Context context;

         public UpdateBalance(Context context) {
             this.context = context;
         }

         @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("tag" , "onPreExecute: progress ba viisble");

        }

        @Override
        protected void onPostExecute(Double s) {
            //Update balance in sharedPreferences
            progressBar.setVisibility(View.GONE);
            Log.d("tag" , "onPostVsible: progress not  viisble");
            Message.message(context,"Updated Balance: "+s);


        }

        @Override
        protected Double doInBackground(Double... doubles) {
            double balance=doubles[0];
            double temoBal=doubles[1];
            balance+=temoBal;

            //Update in database

            return balance;
        }
    }
}

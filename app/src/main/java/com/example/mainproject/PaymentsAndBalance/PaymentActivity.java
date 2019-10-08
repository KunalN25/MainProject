package com.example.mainproject.PaymentsAndBalance;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;

public class PaymentActivity extends AppCompatActivity implements ProceedToPayFragment.ProceedToPayFragmentMethods, PaymentSuccessfulFragment.PaymentSuccessfullMethods {
    private static final String TAG="kun";
    private int totalPrice;
    private double userBalance;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    boolean paymentSuccess,iscanceled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialize();
        if(findViewById(R.id.paymentPage)!=null)
        {
            if(savedInstanceState!=null)
                return;
            transaction=manager.beginTransaction();
            if(getIntent().getBooleanExtra("AddBalance",false))
            {
                startAddBalanceFragment();
            }
            else {
                ProceedToPayFragment proceedToPayFragment = new ProceedToPayFragment();
                transaction.add(R.id.paymentPage, proceedToPayFragment).commit();
                proceedToPayFragment.getTotalPrice(totalPrice);
            }
        }

    }

    private void startAddBalanceFragment() {
        transaction.replace(R.id.paymentPage,new AddBalance()).commit();
    }

    private void initialize() {
        try{
        totalPrice=getIntent().getIntExtra("TotalPrice",0);
    }catch (NullPointerException e)
        {
            Message.message(this,e.toString());
        }
        manager=getSupportFragmentManager();
        userBalance=getUserBalance();
        paymentSuccess=false;


    }

    private double getUserBalance(){
        return 900;//Get the balance stored in sharedPreferences
    }
    @Override
    public void proceedToPay(double grandTotal) {
        if(grandTotal>getUserBalance()){
            //Create a Dialog fragmetn showing not sufficient balance and ADD balance button
            Message.message(this,"Not enough balance");
        }
        else{
            //Paying dialog fragment with progress Bar and the balance is deducted in background using AsyncTask
            //Store the new balance in sharedPreferences and database
            userBalance-=grandTotal;
            Message.message(this,"payment successful\nBalance left: "+userBalance);
            transaction=manager.beginTransaction();
            transaction.replace(R.id.paymentPage,new PaymentSuccessfulFragment()).commit();

        }
    }

    @Override
    public void setPaymentCanceled(boolean cancel) {
        iscanceled=cancel;
        onBackPressed();
    }

    @Override
    public void onBackPressed() {

        if(paymentSuccess || iscanceled)
            super.onBackPressed();
        else
        {
            Log.d(TAG, "onBackPressed: Payment is not successful");
        }

    }

    @Override
    public void setSuccessful(boolean success) {
        paymentSuccess=success;
        Log.d(TAG, "setSuccessful: Success : "+paymentSuccess);
    }
}

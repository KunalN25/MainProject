package com.example.mainproject.PaymentsAndBalance;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity implements AddBalance.AddBalanceMethods,ProceedToPayFragment.ProceedToPayFragmentMethods, PaymentSuccessfulFragment.PaymentSuccessfullMethods {
    private static final String TAG="kun";
    private int totalPrice;
    ActionBar actionBar;

    private double userBalance;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    boolean paymentSuccess,iscanceled,addBalanceFromMainActivity;


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
                addBalanceFromMainActivity=true;
                startAddBalanceFragment();
            }
            else {
                /*START PROCEED TO PAY FRAGMENT*/
                ProceedToPayFragment proceedToPayFragment = new ProceedToPayFragment();
                transaction.add(R.id.paymentPage, proceedToPayFragment).commit();
                proceedToPayFragment.getTotalPrice(totalPrice);
            }
        }

    }

    /*START ADD BALANCE FRAGMENT*/
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
        addBalanceFromMainActivity=false;
        actionBar = getActionBar();



    }

    private double getUserBalance(){
        return UserAccountBalance.USER_BALANCE;//Get the balance stored in sharedPreferences
    }
    @Override
    public void proceedToPay(double grandTotal) {
        if(grandTotal>getUserBalance()){
            Message.message(this,"Not enough balance");
        }
        else{
            //Paying dialog fragment with progress Bar and the balance is deducted in background using AsyncTask
            //Store the new balance in sharedPreferences and database
            userBalance-=grandTotal;
            Message.message(this, "payment successful\nBalance left: " + String.format("%.2f", userBalance));
            transaction=manager.beginTransaction();
            transaction.replace(R.id.paymentPage,new PaymentSuccessfulFragment()).commit();
            UserAccountBalance.USER_BALANCE=userBalance;
            updateBalanceInDatabase();

        }
    }
    @Override
    public  void updateBalanceInDatabase() {
        Log.d(TAG, "updateBalanceInDatabase: called");
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        try {
            DatabaseReference dRef = FirebaseDatabase.getInstance().getReference(user.getUid());
            dRef.child("balance").setValue(UserAccountBalance.USER_BALANCE);
        }catch (NullPointerException ne){
            Message.message(this,ne.toString());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(AddBalance.listenToTouchEvents)
        {
            AddBalance.updateBalance.cancel(true);
            AddBalance.listenToTouchEvents=false;
            return true;
        }
        return false;
    }

    @Override
    public void setPaymentCanceled(boolean cancel) {
        iscanceled=cancel;
        onBackPressed();
    }

    @Override
    public void onBackPressed() {

        if(paymentSuccess || iscanceled || addBalanceFromMainActivity)
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

    @Override
    public void notifyOrderPlaced() {

    }
}
/*ADD
* 1.Balance field in database
* 2.Load balance into the variable
* 3.Keep a separate class with a balance field especially for BALANCE and the balance will be stored in this throughout the session*/
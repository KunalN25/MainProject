package com.example.mainproject.UserProfilePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainproject.LoginAndRegistration.InternetConnection;
import com.example.mainproject.PaymentsAndBalance.PaymentActivity;
import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.UtilityClasses.NoInternetString;

import java.util.Objects;

public class AccountFragment extends Fragment implements AdapterView.OnItemClickListener {
    public AccountFragment(){}
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private AccountFragmentMethods accountFragmentMethods;
    private static final String TAG="AccountFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_account,container,false);
        String[] listItems = {"Profile", "Add Balance", "Log Out"};
        listView=v.findViewById(R.id.accountList);
        arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.textview_for_lists, listItems);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a=(Activity)context;
        accountFragmentMethods=(AccountFragmentMethods)a;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(((TextView) view).getText().toString().equals("Profile"))
        {
            /*START PAYMENT ACTIVITY'S ADD BALANCE FRAGMENT*/
            accountFragmentMethods.startProfileActivity();
            Log.d(TAG, "onItemClick: Profile clicked");
        }
        else if(((TextView) view).getText().toString().equals("Add Balance"))
        {
            Intent intent=new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra("AddBalance",true);
            startActivity(intent);
            Log.d(TAG, "onItemClick: Add Balance");
        } else if(((TextView) view).getText().toString().equals("Log Out"))
        {
            Log.d(TAG, "onItemClick: LogOut clicked");
            if(InternetConnection.isInternetConnected(getActivity())) {
                accountFragmentMethods.logOut();
            }
            else
                Message.message(getActivity(), NoInternetString.NO_INTERNET_CONNECTION);

        }

    }
    public interface AccountFragmentMethods{
         void logOut();
         void startProfileActivity();
    }
}

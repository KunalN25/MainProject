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

import com.example.mainproject.PaymentsAndBalance.PaymentActivity;
import com.example.mainproject.R;

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
        String[] listItems={"Profile","Add Balance","Settings","Log Out"};
        listView=v.findViewById(R.id.accountList);
        arrayAdapter=new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_list_item_1,listItems);
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
            startActivity(new Intent(getActivity(),UserProfileActivity.class));
            Log.d(TAG, "onItemClick: Profile clicked");
        }
        else if(((TextView) view).getText().toString().equals("Add Balance"))
        {
            Intent intent=new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra("AddBalance",true);
            startActivity(intent);
            Log.d(TAG, "onItemClick: Add Balance");
        }
        else if(((TextView) view).getText().toString().equals("Settings"))
        {
            Log.d(TAG, "onItemClick: Settings clicked");
        }
        else if(((TextView) view).getText().toString().equals("Log Out"))
        {
            Log.d(TAG, "onItemClick: LogOut clicked");
            accountFragmentMethods.logOut();
        }

    }
    public interface AccountFragmentMethods{
         void logOut();
    }
}

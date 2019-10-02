package com.example.mainproject;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProvideNewEmail extends DialogFragment implements View.OnClickListener {
    ReProvideEmail reProvideEmail;
    EditText newEmail;
    Button submit;
    public ProvideNewEmail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_provide_new_email, container, false);
        newEmail=v.findViewById(R.id.newEmail);
        submit=v.findViewById(R.id.submitNewEmail);
        submit.setOnClickListener(this);

        return v;
    }
    public void onAttach(Context context){
        super.onAttach(context);
        Activity a=(Activity) context;
        reProvideEmail=(ReProvideEmail) a;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: Submit was clicked");
        dismiss();
        reProvideEmail.updateEmail(newEmail.getText().toString());
    }


    interface ReProvideEmail{
        void updateEmail(String email);
    }
}

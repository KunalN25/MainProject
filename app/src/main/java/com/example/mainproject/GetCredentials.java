package com.example.mainproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class GetCredentials extends DialogFragment implements View.OnClickListener {
    Button submit;
    EditText email,pass;
    Communicator comm;
    private String TAG="GetCredentials";
    public GetCredentials() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_dialog, null);
        submit=v.findViewById(R.id.submitButton);
        email=v.findViewById(R.id.profileChangeEmail);
        pass=v.findViewById(R.id.profileChangePassword);

        submit.setOnClickListener(this);

        setCancelable(true);
        //When the value is false the dialog wont exit if the outside screen is clicked
        return v;
    }


    public void onAttach(Context context){
        super.onAttach(context);
        Activity a=(Activity) context;
        comm=(Communicator)a;
    }

    @Override
    public void onClick(View view) {
        String em=email.getText().toString();
        String ps=pass.getText().toString();
        Log.d(TAG, "onClick: Submit was clicked");
        dismiss();
        comm.submitCredentials(em,ps);

    }


    interface Communicator{
        void submitCredentials(String s1,String s2);
    }
}

package com.example.mainproject.UserProfilePage;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProvideNewEmail extends DialogFragment implements View.OnClickListener {
    private ReProvideEmail reProvideEmail;
    private EditText newEmail;
    private Button submit;
    public ProvideNewEmail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        if(!isGmailAddress(newEmail.getText().toString())){
            Message.message(getActivity(),"Enter valid email");
            return;
        }

        dismiss();
        reProvideEmail.updateEmail(newEmail.getText().toString());
    }
    private boolean isGmailAddress(String emailAddress) {
        String expression = "^[\\w.+\\-]+@gmail\\.com$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public interface ReProvideEmail{
        void updateEmail(String email);
    }
}

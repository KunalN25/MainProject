package com.example.mainproject.RestaurantOperations.Reviews;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.example.mainproject.LoginAndRegistration.UserData;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReviewFragment extends Fragment implements View.OnClickListener {

    EditText rating, revText;
    Button submit;
    Toolbar toolbar;
    String resName;
    FirebaseUser user;
    DatabaseReference reference;
    boolean reviewFlag = false;

    public AddReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_review, container, false);
        initialize(v);
        //  getActivity().setActionBar(toolbar);
        revText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    submit.setEnabled(false);
                else
                    submit.setEnabled(true);
            }
        });
        submit.setOnClickListener(this);
        return v;
    }

    private void initialize(View v) {
        rating = v.findViewById(R.id.userRating);
        revText = v.findViewById(R.id.userReviewText);
        submit = v.findViewById(R.id.submitReview);
        //  toolbar=v.findViewById(R.id.addReviewToolbarr);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Reviews");
    }


    @Override
    public void onClick(View v) {
        String rat = rating.getText().toString(), rev = revText.getText().toString();
        UserData userData = UserData.getCurrentUserObject(getActivity(), user);
        resName = resName.replace(".", " ");
        reference.child(resName).child(user.getUid()).
                setValue(new ReviewData(userData.getFirstName() + " " + userData.getLastName(),
                        "", rev,
                        Double.parseDouble(rat)));
        // reference.child(user.getUid()).child(resName).child("Review Flag")
        //       .setValue(true);


    }

    public void getResName(String n) {
        resName = n;
    }
}

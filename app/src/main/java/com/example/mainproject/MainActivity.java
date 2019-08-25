package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private FirebaseAuth mAuth;


    Intent logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        logIn=new Intent(this,LoginPage.class);
        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(logIn);
            finish();       //This will erase the activity from the backstack
        }
        setContentView(R.layout.activity_main);
    }
    public void logOut(View view) {
        mAuth.signOut();
        startActivity(logIn);
        finish();
    }
}

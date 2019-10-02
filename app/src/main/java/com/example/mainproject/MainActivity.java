package com.example.mainproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.mainproject.LoginAndRegistration.LoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity {
    private static final String TAG="kun";
    private FirebaseAuth mAuth;

    FirebaseFirestore databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redirectToLogin();
        setContentView(R.layout.activity_main);
        initialize();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.dropdown_for_main_activity,menu);
        Log.d(TAG, "onCreateOptionsMenu: called");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile:
                Log.d(TAG, "onOptionsItemSelected: Profile selected");
                goToProfileActivity();
                break;
            case R.id.logout:
                Log.d(TAG, "onOptionsItemSelected: LogOut");
                logOut();
                break;
            case R.id.settings:
                Log.d(TAG, "onOptionsItemSelected: Settings");
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize()    //Put every variable initialization in this function
    {
        databaseRef = FirebaseFirestore.getInstance();

    }
    public void redirectToLogin()
    {
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(this, LoginPage.class));
            finish();       //This will erase the activity from the back stack
        }
    }
    public void logOut() {
        mAuth.signOut();
        startActivity(new Intent(this, LoginPage.class));
        finish();
    }

    public void goToProfileActivity() {
        startActivity(new Intent(this,UserProfileActivity.class));
    }
}

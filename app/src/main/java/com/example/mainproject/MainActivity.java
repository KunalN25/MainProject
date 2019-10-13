package com.example.mainproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mainproject.LoginAndRegistration.LoginPage;
import com.example.mainproject.LoginAndRegistration.UserData;
import com.example.mainproject.RestaurantOperations.RestaurantsList.HomeFragment;
import com.example.mainproject.PaymentsAndBalance.UserAccountBalance;
import com.example.mainproject.UserProfilePage.AccountFragment;
import com.example.mainproject.UtilityClasses.SharePreferencesHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements MapFragment.MapFragmentMethods, AccountFragment.AccountFragmentMethods, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG="Main";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    FirebaseFirestore databaseRef;
    FirebaseUser firebaseUser;
    SharePreferencesHelper sharePreferencesHelper;
    private static final int ERROR_DIALOG_REQUEST=9001;
    HomeFragment homeFragment;
    MapFragment mapFragment;

    @SuppressLint("StaticFieldLeak")

    private static Context context;
    private static boolean animationFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAnimation();
        redirectToLogin();
        setContentView(R.layout.activity_main);

        initialize();

        loadIntoSharedPreferences();
        if (isServicesOK()) {
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.nav_map);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
        }

    }


    public void initialize()    //Put every variable initialization in this function
    {
        firebaseUser=mAuth.getCurrentUser();
        databaseRef = FirebaseFirestore.getInstance();
        if(firebaseUser!=null)
            databaseReference=FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        sharePreferencesHelper = new SharePreferencesHelper(this);
        context=getApplicationContext();
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();


    }

    private void startAnimation() {
        if (animationFlag) {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            animationFlag = false;
        }
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
   
    //Load all the userData into SharedPreferences
    private void loadIntoSharedPreferences() {

        if(databaseReference!=null){

            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData userData=dataSnapshot.getValue(UserData.class);
                assert userData != null;
                sharePreferencesHelper.addToPreference("FirstName",userData.getFirstName());
                sharePreferencesHelper.addToPreference("LastName",userData.getLastName());
                sharePreferencesHelper.addToPreference("MobileNumber",userData.getMobileNo()+"");
                UserAccountBalance.USER_BALANCE=userData.getBalance();
                Log.d(TAG, "onDataChange: Data loaded");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Data could not be loaded");
            }
        });}


    }



    @Override
    public void logOut() {
        mAuth.signOut();
        sharePreferencesHelper.clearPreferences();
        startActivity(new Intent(this,LoginPage.class));
        finish();
        Log.d(TAG, "logOut: Logged out");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment=null;

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                selectedFragment = homeFragment;
                break;

            case R.id.nav_map:
                if(isServicesOK())
                    selectedFragment = mapFragment;
                else
                    selectedFragment=null;//Make an error fragment
                break;

            case R.id.nav_profile:
                selectedFragment=new AccountFragment();
                break;

        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment)
                                                        .commit();
        return true;
    }
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK:checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available== ConnectionResult.SUCCESS){
            Log.d(TAG,"Google play services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"An error occured but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this,"We can't make map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void sendLocationDetails(double latitude, double longitude) {
        Log.d("MapTest", "sendLocationDetails: in MainActivty latitude " + latitude + " longit " + longitude);
        homeFragment.getLocationDetails(latitude, longitude);
    }
}

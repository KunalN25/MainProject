package com.example.mainproject.LoginAndRegistration;

import android.content.Context;

import com.example.mainproject.PaymentsAndBalance.UserAccountBalance;
import com.example.mainproject.UtilityClasses.SharePreferencesHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserData {
    private String Email;
    private String FirstName;
    private String LastName;
    private long MobileNo;
    private double Balance;
    public  UserData()  //Always declare an empty constructor while taking data from firebase in the form of an object
    {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public long getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(long mobileNo) {
        MobileNo = mobileNo;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public static UserData getCurrentUserObject(Context context, FirebaseUser user) {

        SharePreferencesHelper helper = new SharePreferencesHelper(context);
        UserData userData = new UserData();
        userData.setBalance(UserAccountBalance.USER_BALANCE);
        userData.setEmail(user.getEmail());
        userData.setFirstName(helper.loadPreferences("FirstName"));
        userData.setLastName(helper.loadPreferences("LastName"));
        userData.setMobileNo(Long.parseLong(helper.loadPreferences("MobileNumber")));
        return userData;
    }
}

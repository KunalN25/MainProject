package com.example.mainproject.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.SharePreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements OnCompleteListener {

    private static String TAG="MainActivity";
    private EditText fname, lname, user, pass, conpass, number;
    private TextInputLayout fnameLayout,lnameLayout,userLayout,passLayout,conpassLayout,numLayout;
    private Button reg;
    private Intent goToVerif;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference dRef;
    private ActionBar actionBar;
    private SharePreferencesHelper helper;
    private Drawable draw;
    private ProgressBar progressBar;

    //androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        loadFromSharedPreference();
        actionBar.setTitle("Register");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setSelection();     //Set the selection such that the cursor directly navigates to the end of  edittext

    }




    private void initialize(){
        /*******VIEWS*******/
        fname=findViewById(R.id.newfname);
        fnameLayout=findViewById(R.id.firstNameLayout);
        lname=findViewById(R.id.newlname);
        lnameLayout=findViewById(R.id.lastNameLayout);
        user=findViewById(R.id.newuser);
        userLayout=findViewById(R.id.newUserLayout);
        pass=findViewById(R.id.newpass);
        passLayout=findViewById(R.id.newPassLayout);
        conpass=findViewById(R.id.conpass);
        conpassLayout=findViewById(R.id.conPassLayout);
        reg=findViewById(R.id.regButton);
        number=findViewById(R.id.mobileNo);
        numLayout=findViewById(R.id.numberLayout);
        progressBar=findViewById(R.id.registerProgressBar);
        /*******FIREBASE VARIABLES*****/
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase=FirebaseDatabase.getInstance();
        dRef=mfirebaseDatabase.getReference();
        /*****OTHER VARIABLES*****/
        actionBar=getSupportActionBar();
        helper=new SharePreferencesHelper(this);
        draw = getDrawable(R.drawable.border_edit);


    }       //Initializes all the views and database variables
    public void signUp(View view) {
        String email = user.getText().toString();
        String password = pass.getText().toString();
        String conpassword=conpass.getText().toString();
        String first,last,mNumber;

        first=fname.getText().toString();
        last=lname.getText().toString();
        mNumber=number.getText().toString();

        boolean validInput=true,focusflag=true;


        clearFocusFromView();

        clearAllEmptyErrorMessages();

        /*****Fields are empty condition****/

        if (email.isEmpty() || password.isEmpty() || first.isEmpty() || last.isEmpty() || mNumber.isEmpty())
        {       //Check if any of the fields are empty
            Log.d(TAG,"Empty condition");
            setEmptyTextErrorMessages(email,password,first,last,mNumber);
            validInput=false;
        }
        /****Mobile number length condition****/
        if(mNumber.length()<10 && !mNumber.isEmpty()){
            Log.d(TAG,"digit condition");
            numLayout.setError("Enter valid phone no.");
            number.requestFocus();
            focusflag=false;
            ///  Message.message(this,"Phone less than 10");

            /// Message.message(this,"Phone less than 10");

            validInput=false;

        }
        if(!isEmailValid(email) && !email.isEmpty()){
            Log.d(TAG,"Email  condition");
            userLayout.setError("Enter valid email ID");
            if(focusflag) {         //Focus flag is to avoid changing of focus the second time
                userLayout.requestFocus(View.FOCUS_UP);
                popUpKeyBoard();
                focusflag=false;
            }




            validInput=false;
        }

        /****Length of password condition****/

        if(password.length()<6 && !password.isEmpty())
        {
            passLayout.setError("Password too Short. Minimum six characters");
            if(focusflag) {
                passLayout.requestFocus(View.FOCUS_UP);
                popUpKeyBoard();
                focusflag=false;
            }

            validInput=false;

        }

        /*****Whether the password is equal to confirm password****/

        if(!conpassword.equals(password)){ //Check the validity of confirm password
            Log.d(TAG,"password condition");
            passLayout.setError("Passwords do not match");
            conpassLayout.setError("Passwords do not match");
            if(focusflag){
                conpassLayout.requestFocus(View.FOCUS_UP);
                popUpKeyBoard();
            }

            validInput=false;
        }



        //Add user to database
        if(validInput) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);         //Disable user interaction
            if(InternetConnection.isInternetConnected(this))        //Check Internet Connection
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, this);
            else
            {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);  //Enable user interaction
                Message.message(this,"No Internet Connection");
            }

        }

    }   //Sign up the user


    @Override
    public void onComplete(@NonNull Task task) {
        String email = user.getText().toString();

        String first,last;
        long nu;
        first=fname.getText().toString();
        last=lname.getText().toString();
        nu= Long.parseLong(number.getText().toString());
        if (task.isSuccessful()) {

            //Add a dialog box displaying this message
            if(isGmailAddress(email)){
                UserData userData=new UserData();
                userData.setFirstName(first);
                userData.setLastName(last);
                userData.setEmail(email);
                userData.setMobileNo(nu);
                userData.setBalance(100);
                addToDatabase(userData);       //Add the additional data to the database corresponding to the user

                Message.message(getApplicationContext(), "Registered Successfully\nProceed to login");
                sendVerificationEmail();

                startActivity(new Intent(Register.this, LoginPage.class));
                finish();
                helper.clearPreferences();      //Clear the data saved for the back operation

            }
            else{
                Message.message(this,"Could not send Verification Mail\nPlease check your Email ID");
                FirebaseUser user=mAuth.getCurrentUser();
                user.delete();          //Delete the user if email is not valid
                mAuth.signOut();
            }


        } else {
            Message.message(getApplicationContext(), "Could not register");
        }
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);  //Enable user interaction

    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful() ) {
                            Log.d(TAG, "Email sent.");
                            Message.message(Register.this,"Verification Mail sent\nPlease check your Email");


                        }
                        else{
                            Message.message(Register.this,"Could not send Verification Mail\nPlease Check your Email ID");

                        }
                    }
                });


    }



    private void addToDatabase(UserData userData ){
        FirebaseUser user=mAuth.getCurrentUser();
        String userID=user.getUid();        //Get the uid of the user

        dRef.child(userID).setValue(userData);
     /*   dRef.child(userID).child("FirstName").setValue(f);
        dRef.child(userID).child("LastName").setValue(l);
        dRef.child(userID).child("MobileNo").setValue(number);*/
        //Add the data to firebase database


    }
    private void loadFromSharedPreference() {
        if(helper.checkParticularKey("FirstName") || helper.checkParticularKey("LastName") ||
                helper.checkParticularKey("MobileNumber") || helper.checkParticularKey("Username") ||
                helper.checkParticularKey("Password") || helper.checkParticularKey("ConfirmPassword")){

            fname.setText(helper.loadPreferences("FirstName"));
            lname.setText(helper.loadPreferences("LastName"));
            number.setText(helper.loadPreferences("MobileNumber"));
            user.setText(helper.loadPreferences("Username"));
            pass.setText(helper.loadPreferences("Password"));
            conpass.setText(helper.loadPreferences("ConfirmPassword"));
        }
    }           //Load all the temporary saved data

    @Override
    public void onBackPressed() {
        Log.d(TAG,"Back is pressed");
        helper.addToPreference("FirstName",fname.getText().toString());
        helper.addToPreference("LastName",lname.getText().toString());
        helper.addToPreference("MobileNumber",number.getText().toString());
        helper.addToPreference("Username",user.getText().toString());
        helper.addToPreference("Password",pass.getText().toString());
        helper.addToPreference("ConfirmPassword",conpass.getText().toString());
        super.onBackPressed();

    }
    private void setSelection() {
        fname.setSelection(fname.getText().length());
        lname.setSelection(lname.getText().length());
        number.setSelection(number.getText().length());
        user.setSelection(user.getText().length());
        pass.setSelection(pass.getText().length());
        conpass.setSelection(conpass.getText().length());
    }
    //Set the error messages for empty text
    private void setEmptyTextErrorMessages(String email, String password, String first, String last,String mNumber) {
        View v=null;
        if(password.isEmpty())   {passLayout.setError("Password can't be empty");v=pass;}
        if(email.isEmpty())     {userLayout.setError("Username can't be empty");v=user;}
        if(mNumber.isEmpty())    {numLayout.setError("Mobile Number can't be empty");v=number;}
        if(first.isEmpty())      {fnameLayout.setError("FirstName can't be empty");v=fname;}
        if(v!=null)
        {
            v.setFocusableInTouchMode(true);
            v.requestFocus();
            popUpKeyBoard();

        }

    }
    private void clearAllEmptyErrorMessages() {
        userLayout.setError(null);
        passLayout.setError(null);
        fnameLayout.setError(null);
        numLayout.setError(null);
        conpassLayout.setError(null);
    }
    private boolean isEmailValid(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }   //Check whether the string is valid email
    private void clearFocusFromView() {
        View focus=getCurrentFocus();
        if(focus!=null)    {
            focus.clearFocus();
        }


    }

    private void popUpKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    private boolean isGmailAddress(String emailAddress) {
        String expression = "^[\\w.+\\-]+@gmail\\.com$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }



    /*Add:
     *
     *
     *
     *
     *
     *
     *
     *  */
}
/*Stack overflow question:How do I know whether the Email address is valid or not?
 * I am creating an Android application which uses Firebase for Authentication and saving User data. Whenever the user registers into my app, a verification email is sent to the user's email address and until he clicks on the verification link he is not allowed to login. But if the user enters any random email while registering and it is in correct format like "dsfdf@sdf.dfs", it should not add the user to the database. You can see the Format of the Email is correct but this email does not exist. Any person can add many such emails. So to avoid any such useless Emails from piling up in my database, I want to set a condition that unless the Email actually exists, it would not be added to my database. How do I achieve this condition? Or is there a feature in Firebase for this? Or should I restrict the email addresses to limited domains such as gmail,yahoo,outlook,etc.?*/
package com.example.mainproject.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.mainproject.MainActivity;
import com.example.mainproject.UtilityClasses.Message;
import com.example.mainproject.R;
import com.example.mainproject.UtilityClasses.SharePreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    EditText username,pass;
    Button in;
    Intent logIn,goToRegister ;
    ProgressBar progressBar;
    TextInputLayout userLayout,passLayout;
    SharePreferencesHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initialize();
        helper.clearPreferences();
        username.setText("");
        pass.setText("");
        // f1.bringToFront();
    }
    private void initialize()
    {
        username=findViewById(R.id.username);
        userLayout=findViewById(R.id.usernameLayout);
        pass=findViewById(R.id.password);
        passLayout=findViewById(R.id.passwordLayout);
        in=findViewById(R.id.loginButton);
        progressBar=findViewById(R.id.loadingProgress);
        progressBar.bringToFront();
        // up=findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                    Log.d(TAG,"User is signed in");
                else
                    Log.d(TAG,"User is signed out");
            }
        };
        helper=new SharePreferencesHelper(this);





    }
    public void register(View view)
    {
        goToRegister=new Intent(getApplicationContext(), Register.class);
        startActivity(goToRegister);

    }

    public void login(View view) {
        String email=username.getText().toString();
        String password=pass.getText().toString();
        clearAllEmptyErrorMessages();

        if (email.isEmpty() || password.isEmpty())
        {
           // Message.message(this,"You didn't fill all the details");
            if(email.isEmpty())     userLayout.setError("Please enter your Email ID");
            if(password.isEmpty())  passLayout.setError("Please enter your password");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);         //Disable user interaction
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                helper.clearPreferences();
                                FirebaseUser user=mAuth.getCurrentUser();
                                if(user.isEmailVerified()) {            //Whether the user is Email Verified
                                    Log.d("kun","Successful login");
                                    logIn = new Intent(LoginPage.this, MainActivity.class);
                                    startActivity(logIn);
                                    finish();
                                }
                                else{
                                    Message.message(LoginPage.this,"This user is not verified");
                                    userLayout.setError("This user is not verified");
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);  //Enable user interaction
                                    mAuth.signOut();
                                }



                            } else {
                                // If sign in fails, display a message to the user.
                                progressBar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);  //Enable user interaction
                                Log.d("kun","Failed signing");
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Message.message(getApplicationContext(), "Authentication failed.");

                            }

                            // ...
                        }
                    });


        }
    }
    private void clearAllEmptyErrorMessages() {
        userLayout.setError(null);
        passLayout.setError(null);

    }

    public void forgotPassword(View view) {
        Log.d(TAG,"Forgot password");
        startActivity(new Intent(this, ForgotPassword.class));





    }

    @Override
    protected void onDestroy() {
        Log.d("share", "onDestroy: of loginPage");
        super.onDestroy();
    }

    /*Add:
     *  1. Asterisk error message on all EditText.
     *  2. Navigate to the first incorrect field
     *  3. Reduce the margin between keyboard and EditText, also between hint and the top of EditText
     */
}

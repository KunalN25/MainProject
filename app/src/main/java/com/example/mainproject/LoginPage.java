package com.example.mainproject;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initialize();
        username.setText("");
        pass.setText("");
    }
    private void initialize()
    {
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
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



    }
    public void register(View view)
    {
       // goToRegister=new Intent(getApplicationContext(),Register.class);
        //startActivity(goToRegister);

    }

    public void login(View view) {
        String email=username.getText().toString();
        String password=pass.getText().toString();

        if (email.isEmpty() || password.isEmpty())
        {
            Message.message(this,"You didn't fill all the details");

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
                                Log.d("kun","Successful login");
                                logIn=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(logIn);
                                finish();



                            } else {
                                // If sign in fails, display a message to the user.
                                progressBar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Log.d("kun","Failed signing");
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Message.message(getApplicationContext(), "Authentication failed.");

                            }

                            // ...
                        }
                    });


        }
    }


    public void forgotPassword(View view) {
        Log.d(TAG,"Forgot password");
        startActivity(new Intent(this,ForgotPassword.class));





    }
}

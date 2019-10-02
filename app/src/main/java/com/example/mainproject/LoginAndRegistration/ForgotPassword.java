package com.example.mainproject.LoginAndRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mainproject.Message;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements OnCompleteListener {
    private EditText email;
    private Button reset;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
    }
    private void initialize(){
        email=findViewById(R.id.verifEmail);
        reset=findViewById(R.id.resetPwd);
        mAuth=FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        String tempEmail=email.getText().toString();
        Message.message(this,tempEmail);
        if(tempEmail.isEmpty()){
            Message.message(this,"Please enter your email ID");
        }
        else
        {
            mAuth.sendPasswordResetEmail(tempEmail).
                    addOnCompleteListener(this);

        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if(task.isSuccessful()){
            Message.message(getApplicationContext(),
                    "Your password has been reset");
            startActivity(new Intent(this, LoginPage.class));
            finish();
        }
        else{
            Message.message(getApplicationContext(),"Recovery failed");
        }
    }
}

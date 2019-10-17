package com.example.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView = findViewById(R.id.title1);
        int splashTimeOut = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("kun", "run: splash is running");
                AnimationClass.ANIMATION_FLAG = false;

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, splashTimeOut);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        textView.startAnimation(animation);
    }
}
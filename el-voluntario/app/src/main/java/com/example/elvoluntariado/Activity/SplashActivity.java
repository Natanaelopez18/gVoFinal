package com.example.elvoluntariado.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elvoluntariado.MainActivity;
import com.example.elvoluntariado.R;
import com.tumblr.remember.Remember;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    Animation animation;

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imgSplash);
        textView = findViewById(R.id.textSplash);

        animation = AnimationUtils.loadAnimation(this,R.anim.transitionsplash);
        imageView.startAnimation(animation);
        textView.startAnimation(animation);
        Remember.init(getApplicationContext(), "avatar");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                prefManager = new PrefManager(SplashActivity.this);
                if (!prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                    finish();
                }
                else {
                    validateSession();
                    finish();
                }

            }
        },3000);
    }

    public void validateSession (){

        Remember.init(getApplicationContext(),"access_token");
        Remember.init(getApplicationContext(), "avatar");

        if (Remember.getString("access_token", "").isEmpty()){
            Remember.remove("avatar");
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(true);

        startActivity(new Intent(SplashActivity.this, TourActivity.class));
        finish();
    }
}

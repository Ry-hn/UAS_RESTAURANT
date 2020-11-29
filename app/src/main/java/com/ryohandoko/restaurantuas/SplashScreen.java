package com.ryohandoko.restaurantuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.view.LoginActivity;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splashlogo_animation);
        ImageView img = findViewById(R.id.splash_logo);
        img.setAnimation(splashAnim);

        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  isLoggedIn()?
                        new Intent(SplashScreen.this, MainActivity.class) :
                        new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);
    }

    private boolean isLoggedIn() {
        SharedPreferences sp = getSharedPreferences("SECRET", MODE_PRIVATE);
        return !sp.getString("token", "").isEmpty();
    }
}
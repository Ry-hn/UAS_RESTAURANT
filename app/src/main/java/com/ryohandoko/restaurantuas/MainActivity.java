package com.ryohandoko.restaurantuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ryohandoko.restaurantuas.util.NotificationUtil;
import com.ryohandoko.restaurantuas.view.fragment.HomeFragment;
import com.ryohandoko.restaurantuas.view.fragment.MapFragment;
import com.ryohandoko.restaurantuas.view.fragment.PesananFragment;
import com.ryohandoko.restaurantuas.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("SECRET", MODE_PRIVATE);

        if(sp.getBoolean("tema", false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView botNav = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(botNav, navController);

        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.Fragment_Home :
                        fragment  = new HomeFragment(); break;
                    case R.id.Fragment_Profile:
                        fragment = new ProfileFragment(); break;
                    case R.id.Fragment_Pesanan:
                        fragment =  new PesananFragment(); break;
                    case R.id.Fragment_Geolocation:
                        fragment =  new MapFragment(); break;
                }

                return loadFragment(fragment);
            }
        });

        new NotificationUtil().createNotification(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "FCM berhasil";
                        if(!task.isSuccessful())
                            msg = "FCM gagal";
                        Log.i("FCM", msg);
                    }
                });

    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            //R.id.fragment = fragment container di

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();

            return true;
        }
        return false;
    }
}
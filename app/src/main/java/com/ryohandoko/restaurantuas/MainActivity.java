package com.ryohandoko.restaurantuas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ryohandoko.restaurantuas.view.fragment.FavoriteFragment;
import com.ryohandoko.restaurantuas.view.fragment.HomeFragment;
import com.ryohandoko.restaurantuas.view.fragment.MapFragment;
import com.ryohandoko.restaurantuas.view.fragment.PesananFragment;
import com.ryohandoko.restaurantuas.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    case R.id.Fragment_Favorite:
                        fragment = new FavoriteFragment(); break;
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

    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            //R.id.fragment = fragment container di layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}
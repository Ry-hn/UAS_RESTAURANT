package com.ryohandoko.restaurantuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.view.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("SECRET", MODE_PRIVATE);

        if(sp.getString("token", "").isEmpty()) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        Toast.makeText(this, "SUDAH LOGIN", Toast.LENGTH_SHORT).show();

        Log.i("MAIN", "onCreate: token" + sp.getString("token",""));

    }
}
package com.ryohandoko.restaurantuas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ryohandoko.restaurantuas.repository.ProductRepository;
import com.ryohandoko.restaurantuas.view.admin.CreateProductActivity;
import com.ryohandoko.restaurantuas.view.admin.CreateUserActivity;
import com.ryohandoko.restaurantuas.view.admin.ShowProductActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView createUser, createProduct, showUser, showProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        createUser = findViewById(R.id.cvCreateuser);
        createProduct = findViewById(R.id.cvCreateProduct);
        showUser = findViewById(R.id.cvShowListUser);
        showProduct = findViewById(R.id.cvShowListProduct);

        createUser.setOnClickListener(this);
        createProduct.setOnClickListener(this);
        showProduct.setOnClickListener(this);
    }

    private void loadActivity(Class<?> clazz) {
        Intent i = new Intent(this, clazz);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvCreateuser: loadActivity(CreateUserActivity.class); break;
            case R.id.cvCreateProduct: loadActivity(CreateProductActivity.class); break;
            case R.id.cvShowListProduct: loadActivity(ShowProductActivity.class); break;
        }
    }
}
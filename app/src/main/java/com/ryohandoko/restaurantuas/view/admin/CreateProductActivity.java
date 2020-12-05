package com.ryohandoko.restaurantuas.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.ActivityCreateProductBinding;
import com.ryohandoko.restaurantuas.viewmodel.CreateProductViewModel;

public class CreateProductActivity extends AppCompatActivity {

    private ActivityCreateProductBinding binding;
    private CreateProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_product);
        viewModel = ViewModelProviders.of(this).get(CreateProductViewModel.class);

        binding.setCreateProductView(this);
        binding.setCreateProductVM(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.setIsLoading(!viewModel.getIsLoading().get());
                Log.i("INPUT", "onChanged: string " + s);
                switch (s) {
                    case "Add Product Success":
                        Toast.makeText(CreateProductActivity.this, "Product Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(CreateProductActivity.this, "Product Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        break;
                }

                onBackPressed();
            }
        });

        binding.executePendingBindings();
    }

    public void back(View view) { onBackPressed(); }

    public void createProduct(View view) {
        if(viewModel.isAllFieldInputted()) viewModel.create();
        else Toast.makeText(this, "Isikan Semua Data!", Toast.LENGTH_SHORT).show();
    }

}
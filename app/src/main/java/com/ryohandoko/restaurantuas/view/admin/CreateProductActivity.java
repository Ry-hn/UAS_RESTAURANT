package com.ryohandoko.restaurantuas.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.ActivityCreateProductBinding;
import com.ryohandoko.restaurantuas.viewmodel.CreateProductViewModel;
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;

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
        viewModel.create();
    }

}
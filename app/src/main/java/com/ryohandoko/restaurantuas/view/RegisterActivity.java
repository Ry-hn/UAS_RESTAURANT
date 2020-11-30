package com.ryohandoko.restaurantuas.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.ActivityRegisterBinding;
import com.ryohandoko.restaurantuas.util.NotificationUtil;
import com.ryohandoko.restaurantuas.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setLifecycleOwner(this);

        binding.setRegVm(viewModel);
        binding.setRegView(this);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // berhentikan progress bar
                viewModel.setIsLoading(!viewModel.getIsLoading().get());

                if(s.equals("Register Success")) {
                    new NotificationUtil().addNotification(RegisterActivity.this, "Registrasi Berhasil", "Silahkan Verifikasi ID lewat email", LoginActivity.class);
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registrasi Gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.executePendingBindings();
    }

    public void createUser(View view) {
        if(viewModel.isAllFieldInputted())
            viewModel.createUser();
        else
            Toast.makeText(this, "Isikan semua field dengan benar", Toast.LENGTH_SHORT).show();
    }
}
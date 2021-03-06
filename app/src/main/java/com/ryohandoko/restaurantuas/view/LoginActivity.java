package com.ryohandoko.restaurantuas.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.AdminActivity;
import com.ryohandoko.restaurantuas.MainActivity;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.ActivityLoginBinding;
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        binding.setLoginVM(viewModel);
        binding.setLoginView(this);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // berhentikan progress bar
                viewModel.setIsLoading(!viewModel.getIsLoading().get());

                switch (s) {
                    case "ADMIN":
                        loadActivity(AdminActivity.class);
                        Toast.makeText(getApplication(), "ADMIN Login", Toast.LENGTH_SHORT).show();
                        break;
                    case "Authenticated":
                        loadActivity(MainActivity.class);
                        Toast.makeText(getApplication(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                        break;
                    case "421":
                        Toast.makeText(getApplication(), "Akun Belum diaktifkan", Toast.LENGTH_SHORT).show();
                        break;
                    case "400":
                    case "401":
                        Toast.makeText(getApplication(), "Username / password salah", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.executePendingBindings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setIsLoading(false);
    }

    private void loadActivity(Class<?> clazz) {
        Intent i = new Intent(LoginActivity.this, clazz);
        startActivity(i);
        finish();
    }

    public void signIn(View view) {
        viewModel.signIn();
    }

    public void SignUp(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
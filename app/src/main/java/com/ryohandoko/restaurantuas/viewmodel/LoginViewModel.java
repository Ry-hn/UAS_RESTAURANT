package com.ryohandoko.restaurantuas.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.ryohandoko.restaurantuas.Model.User;
import com.ryohandoko.restaurantuas.repository.UserRepository;

import java.util.Objects;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private final UserRepository repository;

    //variable ini digunakan untuk data binding view
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);


    // response dari repository request
    public  LiveData<String> errorMessage;
    private LiveData<User> userLiveData;


    // observer msg untuk menentukan hasil request
    private Observer<String> observer = o -> {
        isLoading.set(!isLoading.get());

        switch (Objects.requireNonNull(errorMessage.getValue())) {
            case "Authenticated":
                Toast.makeText(getApplication(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                break;
            case "421":
                Toast.makeText(getApplication(), "Akun Belum diaktifkan", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplication(), "Username / password salah", Toast.LENGTH_SHORT).show();
                break;
        }
    };

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(getApplication());
        isLoading.set(false);
    }

    public void signIn(View view) {
        isLoading.set(true);
        userLiveData = repository.login(this.email.get(), this.password.get());
        errorMessage = repository.getErrorMessage();
        errorMessage.observeForever(observer);

        Log.i(TAG, "signIn: Button pressed errorMsg: " + errorMessage.getValue());
    }

    @Override
    protected void onCleared() {
        errorMessage.removeObserver(observer);
        super.onCleared();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }


    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableField<Boolean> isLoading) {
        this.isLoading = isLoading;
    }
}

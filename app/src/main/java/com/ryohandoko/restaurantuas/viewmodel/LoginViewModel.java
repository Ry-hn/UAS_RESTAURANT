package com.ryohandoko.restaurantuas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ryohandoko.restaurantuas.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private final UserRepository repository;

    //variable ini digunakan untuk data binding view
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        isLoading.set(false);
    }

    public void signIn() {
        isLoading.set(true);
        repository.login(this.email.get(), this.password.get());
    }

    @Override
    protected void onCleared() { super.onCleared(); }

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

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public LiveData<String> getErrorMessage() { return repository.getErrorMessage(); }
}

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
    private ObservableField<String> email;
    private ObservableField<String> password;
    private ObservableField<Boolean> isLoading;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        email = new ObservableField<>("");
        password = new ObservableField<>("");
        isLoading = new ObservableField<>(false);

        repository = new UserRepository(application);
        isLoading.set(false);
    }

    public void signIn() {
        isLoading.set(true);
        repository.login(this.email.get(), this.password.get());
    }

    @Override
    protected void onCleared() { super.onCleared(); }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getPassword() {
        return password;
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

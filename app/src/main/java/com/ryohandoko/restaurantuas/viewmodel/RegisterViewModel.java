package com.ryohandoko.restaurantuas.viewmodel;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {

    private final RegisterRepository repository;
    private final ObservableField<String> name = new ObservableField<>("");
    private final ObservableField<String> email = new ObservableField<>("");
    private final ObservableField<String> telepon = new ObservableField<>("");
    private final ObservableField<String> password = new ObservableField<>("");
    private final ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public RegisterViewModel() {
        super();
        repository = new RegisterRepository();
        isLoading.set(false);
    }

    public void createUser() {
        isLoading.set(true);
        repository.register(this.email.get(), this.name.get(), this.password.get(), this.telepon.get(), "USER");
    }

    public boolean isAllFieldInputted() {
        return !TextUtils.isEmpty(name.get()) &&
                !TextUtils.isEmpty(email.get()) &&
                !TextUtils.isEmpty(password.get()) &&
                !TextUtils.isEmpty(telepon.get());
    }

    @Override
    protected void onCleared() { super.onCleared(); }

    public ObservableField<String> getName() { return name; }
    public void setName(String name) { this.name.set(name);}

    public ObservableField<String> getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon.set(telepon);}

    public ObservableField<String> getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getPassword() { return password; }
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

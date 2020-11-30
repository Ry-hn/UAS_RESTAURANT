package com.ryohandoko.restaurantuas.viewmodel;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;

import com.ryohandoko.restaurantuas.repository.RegisterRepository;
import com.ryohandoko.restaurantuas.util.UserValidator;

public class RegisterViewModel extends ViewModel {

    private final RegisterRepository repository;
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> telepon = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public RegisterViewModel() {
        super();
        repository = new RegisterRepository();
        isLoading.set(false);
    }

    public void createUser() {
        isLoading.set(true);
        repository.register(this.email.get(), this.name.get(), this.password.get(), this.telepon.get(), "USER");
    }

    @BindingAdapter({"validation", "errorMsg"})
    public static void setErrorEnable(final TextInputLayout txtLayout, final UserValidator.StringRule strRule, final String errorMsg) {
        txtLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (strRule.validate(txtLayout.getEditText().getText())) {
                    txtLayout.setError(errorMsg);
                } else {
                    txtLayout.setError(null);
                }
            }
        });
    }

    public boolean isAllFieldInputted() {
        return !TextUtils.isEmpty(name.get()) &&
                !TextUtils.isEmpty(email.get()) &&
                !TextUtils.isEmpty(password.get()) &&
                !TextUtils.isEmpty(telepon.get());
    }


    @Override
    protected void onCleared() { super.onCleared(); }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name);}

    public String getTelepon() { return telepon.get(); }
    public void setTelepon(String telepon) { this.telepon.set(telepon);}

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

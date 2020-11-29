package com.ryohandoko.restaurantuas.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ryohandoko.restaurantuas.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final ProfileRepository repository;
    private SharedPreferences sp;

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> gambar = new ObservableField<>("");
    public ObservableField<String> telepon = new ObservableField<>("");
    public ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public ProfileViewModel(Application app) {
        super(app);
        sp = app.getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        repository = new ProfileRepository();

        repository.getUser(sp.getString("token", ""));
    }

    public void LogOut(View view) {
        repository.logout(sp.getString("token", ""));
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

    public String getGambar() {
        return gambar.get();
    }
    public void setGambar(String gambar) {
        this.gambar.set(gambar);
    }
    
    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }
    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public LiveData<String> getErrorMessage() { return repository.getErrorMessage(); }

}

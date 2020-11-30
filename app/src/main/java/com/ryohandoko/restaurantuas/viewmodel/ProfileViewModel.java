package com.ryohandoko.restaurantuas.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ryohandoko.restaurantuas.Model.User;
import com.ryohandoko.restaurantuas.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private static final String TAG = "ProfileViewModel";
    private final ProfileRepository repository;
    private SharedPreferences sp;

    private final ObservableField<String> name = new ObservableField<>("");
    private final ObservableField<String> email = new ObservableField<>("");
    private final ObservableField<String> gambar = new ObservableField<>("");
    private final ObservableField<String> telepon = new ObservableField<>("");
    private final ObservableField<Boolean> isLoading = new ObservableField<>(false);


    public ProfileViewModel(Application app) {
        super(app);
        sp = app.getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        repository = new ProfileRepository();
        isLoading.set(true);
        repository.getUser(sp.getString("token", ""));
    }

    public void LogOut() {
        repository.logout(sp.getString("token", ""));
    }

    public void Capture() {

    }

    @Override
    protected void onCleared() { super.onCleared(); }

    public ObservableField<String> getName() { return name; }
    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getTelepon() { return telepon; }
    public void setTelepon(String telepon) {
        this.telepon.set(telepon);
    }

    public ObservableField<String> getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar.set(gambar); }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }
    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public LiveData<String> getErrorMessage() { return repository.getErrorMessage(); }
    public LiveData<User> getUserLiveData() { return repository.getUserMutableLiveData(); }

}

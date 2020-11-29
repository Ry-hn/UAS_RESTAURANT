package com.ryohandoko.restaurantuas.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiUserInterface;
import com.ryohandoko.restaurantuas.API.Response.UserResponse;
import com.ryohandoko.restaurantuas.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static final String TAG = "userrepository";

    private static UserRepository instace;
    private ApiUserInterface apiService;

    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<String> errorMessage;

    // untuk shared pref
    private Application application;

    private UserRepository(Application app) {
        apiService = ApiClient.getClient().create(ApiUserInterface.class);
        userMutableLiveData = new MutableLiveData<>();

        errorMessage = new MutableLiveData<>();
        application = app;
    }

    public static UserRepository getInstance(Application app) {
        if (instace == null)
            instace = new UserRepository(app);

        return instace;
    }

    public void login(String email, String password) {

        Call<UserResponse> request = apiService.userLogin(email, password);
        
        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //response 200 ada body(), selain itu nda ada makanya nanti npe
                if(response.code() == 200) {

                    if(response.body().getMessage().equals("Authenticated")) {
                        userMutableLiveData.setValue(response.body().getUser());

                        //menyimpan id dan token kedalam shared pref
                        saveIdToken(userMutableLiveData.getValue().getId(),
                                        response.body().getAccess_token());
                    }

                    errorMessage.postValue(response.body().getMessage());
                }
                else {
                    errorMessage.postValue(Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // gagal server langsung kasih kode 500
                errorMessage.postValue("500");
            }
        });
    }

    private void saveIdToken(String id, String token) {
        SharedPreferences sp = application.getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("id", id);
        editor.putString("token", token);

        editor.apply();
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }

    public LiveData<User> getUserMutableLiveData() { return userMutableLiveData; }
}

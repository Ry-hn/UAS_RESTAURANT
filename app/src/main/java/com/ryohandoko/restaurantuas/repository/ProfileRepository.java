package com.ryohandoko.restaurantuas.repository;

import android.app.Application;
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

public class ProfileRepository {
    private final ApiUserInterface apiService;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<User> userMutableLiveData;

    public ProfileRepository() {
        apiService = ApiClient.getClient().create(ApiUserInterface.class);

        userMutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public void getUser(String token) {
        String auth = "Bearer " + token;
        Call<UserResponse> request = apiService.getCurrentUser(auth);

        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {
                    userMutableLiveData.postValue(response.body().getUser());
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                errorMessage.postValue("500");
            }
        });
    }

    public void updateUser(String token, String nama, String telepon) {
        String auth = "Bearer " + token;
        Call<UserResponse> request = apiService.getCurrentUser(auth);

        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                errorMessage.postValue("500");
            }
        });
    }

    public void logout(String token) {
        String auth = "Bearer " + token;

        Call<UserResponse> request = apiService.logout(auth);

        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                errorMessage.postValue("500");
            }
        });
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }

    public LiveData<User> getUserMutableLiveData() { return userMutableLiveData; }
}

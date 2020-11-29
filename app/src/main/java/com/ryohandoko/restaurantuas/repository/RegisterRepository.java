package com.ryohandoko.restaurantuas.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiUserInterface;
import com.ryohandoko.restaurantuas.API.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    private final ApiUserInterface apiService;
    private final MutableLiveData<String> errorMessage;

    public RegisterRepository() {
        apiService = ApiClient.getClient().create(ApiUserInterface.class);
        errorMessage = new MutableLiveData<>();
    }

    public void register(String email, String nama, String password, String telepon, String role) {
        Call<UserResponse> request = apiService.userRegister(email, password, role, nama, telepon);

        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) errorMessage.postValue(response.body().getMessage());
                else errorMessage.postValue(Integer.toString(response.code()));
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                errorMessage.postValue("500");
            }
        });
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
}

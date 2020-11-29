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
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static final String TAG = "userrepository";

    private static UserRepository instace;
    private ApiUserInterface apiService;

    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<String> errorMessage;

    private Application application;
    private String message;

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

    public LiveData<User> login(String email, String password) {

        Call<UserResponse> request = apiService.userLogin(email, password);
        
        request.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 200) {
                    message = response.body().getMessage();

                    if(message.equals("Authenticated")) {
                        userMutableLiveData.setValue(response.body().getUser());
                        Log.i(TAG, "onResponse: user  " + response.body().getUser().getNama_user() + " berhasil didapatkan");

                        //menyimpan id dan token kedalam shared pref
                        saveIdToken(userMutableLiveData.getValue().getId(),
                                        response.body().getAccess_token());
                    }
                    errorMessage.setValue(response.body().getMessage());
                }
                else {
                    Log.i(TAG, "onResponse: error code -> " + response.code());
                    errorMessage.setValue(Integer.toString(response.code()));
                }

                Log.i(TAG, "login: error msg" + errorMessage.getValue());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                message = "500";
                errorMessage.setValue("500");

                Log.i(TAG, "login: error msg" + errorMessage.getValue());
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });

        return userMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    private void saveIdToken(String id, String token) {
        SharedPreferences sp = application.getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("id", id);
        editor.putString("token", token);

        editor.apply();
    }
}

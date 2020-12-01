package com.ryohandoko.restaurantuas.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiItemInterface;
import com.ryohandoko.restaurantuas.API.Response.ItemResponse;
import com.ryohandoko.restaurantuas.Model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final ApiItemInterface apiService;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<List<Item>> ListProductLiveData;

    public ProductRepository() {
        this.apiService = ApiClient.getClient().create(ApiItemInterface.class);

        this.errorMessage = new MutableLiveData<>();
        this.ListProductLiveData = new MutableLiveData<>();
    }

    public void getProducts() {
        Call<ItemResponse> request = apiService.getAllProduct();

        request.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.code() == 200) {
                    ListProductLiveData.postValue(response.body().getListItems());
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                errorMessage.postValue("500");
            }
        });
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<List<Item>> getListProductLiveData() { return ListProductLiveData; }
}

package com.ryohandoko.restaurantuas.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiItemInterface;
import com.ryohandoko.restaurantuas.API.Response.ItemResponse;
import com.ryohandoko.restaurantuas.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final ApiItemInterface apiService;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<List<Item>> ListProductLiveData;

    private final MutableLiveData<Item> ProductLiveData;


    public ProductRepository() {
        this.apiService = ApiClient.getClient().create(ApiItemInterface.class);

        this.errorMessage = new MutableLiveData<>();
        this.ListProductLiveData = new MutableLiveData<>();
        this.ProductLiveData = new MutableLiveData<>();
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
                Log.i("CREATEPRODUCT", "onFailure: "  + t.getMessage());
                errorMessage.postValue("500");
            }
        });
    }

    public void createProduct(String nama, String deskripsi, String harga, String url) {
        Call<ItemResponse> request = apiService.createProduct(nama, deskripsi, harga, url);

        request.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.code() == 200) {
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

    public void getProductById(String id) {
        Call<ItemResponse> request = apiService.getProductById(id);

        request.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.code() == 200) {
                    ProductLiveData.postValue(response.body().getItem());

                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                errorMessage.postValue("500");
                Log.i("RETRIEVEPRODUCT", "onFailure: " + t.getMessage());
            }
        });
    }


    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<List<Item>> getListProductsLiveData() { return ListProductLiveData; }
    public LiveData<Item> getProductLiveData() { return ProductLiveData; }
}

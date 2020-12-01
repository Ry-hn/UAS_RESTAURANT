package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiItemInterface {

    @GET("api/product")
    Call<ItemResponse> getAllProduct();

}

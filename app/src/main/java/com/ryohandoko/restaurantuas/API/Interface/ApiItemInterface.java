package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.ItemResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiItemInterface {

    @GET("api/product")
    Call<ItemResponse> getAllProduct();

    @POST("api/product")
    @FormUrlEncoded
    Call<ItemResponse> createProduct(@Field("nama_product") String nama,
                                     @Field("deskripsi_product") String deskripsi,
                                     @Field("harga_product") String harga,
                                     @Field("gambar_product") String url);

}

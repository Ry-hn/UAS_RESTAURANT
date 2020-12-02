package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.ItemResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiItemInterface {

    @GET("api/product")
    Call<ItemResponse> getAllProduct();

    @POST("api/product")
    @FormUrlEncoded
    Call<ItemResponse> createProduct(@Field("nama_product") String nama,
                                     @Field("deskripsi_product") String deskripsi,
                                     @Field("harga_product") String harga,
                                     @Field("gambar_product") String url);

    @GET("api/product/{id}")
    Call<ItemResponse> getProductById(@Path("id") String id);

}

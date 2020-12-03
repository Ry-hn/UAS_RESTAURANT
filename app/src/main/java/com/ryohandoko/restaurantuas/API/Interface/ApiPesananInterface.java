package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.PesananResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiPesananInterface {

    @POST("api/pesanan")
    @FormUrlEncoded
    Call<PesananResponse> tambahPesanan(@Field("id_user") String idUser, @Field("id_product") String idproduct, @Field("jumlah_pesan") String jumlah);

    @GET("api/pesanan/{id}")
    Call<PesananResponse> getPesananUser(@Path("id") String idUser);

}

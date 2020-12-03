package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.PesananResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiPesananInterface {

    @POST("api/pesanan")
    @FormUrlEncoded
    Call<PesananResponse> tambahPesanan(@Field("id_user") String idUser, @Field("id_product") String idproduct, @Field("jumlah_pesan") String jumlah);



}

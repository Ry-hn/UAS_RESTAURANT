package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUserInterface {

    @POST("login")
    @FormUrlEncoded
    Call<UserResponse> userLogin(@Field("email") String email,
                                    @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<UserResponse> userRegister(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("role") String role,
                                    @Field("nama_user") String nama_user,
                                    @Field("telepon") String telepon);

    @GET("/api/user")
    Call<UserResponse> getCurrentUser(@Header("Authorization") String authHeader);

    @POST("logout")
    Call<UserResponse> logout(@Header("Authorization") String authHeader);
}

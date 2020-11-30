package com.ryohandoko.restaurantuas.API.Interface;

import com.ryohandoko.restaurantuas.API.Response.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiUserInterface {

    @POST("api/login")
    @FormUrlEncoded
    Call<UserResponse> userLogin(@Field("email") String email,
                                    @Field("password") String password);

    @POST("api/register")
    @FormUrlEncoded
    Call<UserResponse> userRegister(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("role") String role,
                                    @Field("nama_user") String nama_user,
                                    @Field("telepon") String telepon);

    @POST("api/user/image")
    @Multipart
    Call<UserResponse> uploadImage(@Header("Authorization") String authHeader,
                                   @Part("id") RequestBody id, @Part MultipartBody.Part gambar);


    @POST("api/logout")
    Call<UserResponse> logout(@Header("Authorization") String authHeader);

    @GET("api/user")
    Call<UserResponse> getCurrentUser(@Header("Authorization") String authHeader);

    @PUT("api/user/{id}")
    @FormUrlEncoded
    Call<UserResponse> updateUser(@Header("Authorization") String authHeader, @Path("id") String id,
                                  @Field("nama_user") String nama, @Field("telepon") String telepon);

    @PUT("api/user/{id}")
    @FormUrlEncoded
    Call<UserResponse> updatePassword(@Header("Authorization") String authHeader,
                                  @Path("id") String id, @Field("oldPassword") String oldPassword,
                                  @Field("newPassword") String newPassword);


}

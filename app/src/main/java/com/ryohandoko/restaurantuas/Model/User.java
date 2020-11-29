package com.ryohandoko.restaurantuas.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("role")
    private String role;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("gambar")
    private String gambar;
}

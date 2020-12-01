package com.ryohandoko.restaurantuas.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryohandoko.restaurantuas.model.User;

public class UserResponse {

    public static final String TOKEN_TYPE = "Bearer ";

    @SerializedName("user")
    @Expose
    private User user = null;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private String access_token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}

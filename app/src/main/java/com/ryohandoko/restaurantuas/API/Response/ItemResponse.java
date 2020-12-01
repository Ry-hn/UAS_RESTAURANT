package com.ryohandoko.restaurantuas.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryohandoko.restaurantuas.model.Item;

import java.util.List;

public class ItemResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Item> listItems = null;

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public List<Item> getListItems() { return listItems; }

    public void setListItems(List<Item> listItems) { this.listItems = listItems; }
}

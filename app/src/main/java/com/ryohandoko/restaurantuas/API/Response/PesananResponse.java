package com.ryohandoko.restaurantuas.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryohandoko.restaurantuas.model.Pesanan;

import java.util.List;

public class PesananResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("pesanan")
    @Expose
    private List<Pesanan> listPesanan = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pesanan> getListPesanan() {
        return listPesanan;
    }

    public void setListPesanan(List<Pesanan> listPesanan) {
        this.listPesanan = listPesanan;
    }
}

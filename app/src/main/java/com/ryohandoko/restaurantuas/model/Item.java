package com.ryohandoko.restaurantuas.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.ryohandoko.restaurantuas.BR;

public class Item extends BaseObservable {

    @SerializedName("id")
    private String id;

    @SerializedName("nama_product")
    private String nama_product;

    @SerializedName("deskripsi_product")
    private String deskripsi_product;

    @SerializedName("harga_product")
    private String harga_product;

    @SerializedName("gambar_product")
    private String gambar;

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getNama_product() {
        return nama_product;
    }

    public void setNama_product(String nama_product) {
        this.nama_product = nama_product;
    }

    public String getDeskripsi_product() {
        return deskripsi_product;
    }

    public void setDeskripsi_product(String deskripsi_product) { this.deskripsi_product = deskripsi_product; }

    public String getHarga_product() {
        return harga_product;
    }

    public void setHarga_product(String harga_product) {
        this.harga_product = harga_product;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

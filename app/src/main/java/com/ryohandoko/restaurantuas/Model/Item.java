package com.ryohandoko.restaurantuas.Model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    private String id;

    @SerializedName("nama_product")
    private String nama_product;

    @SerializedName("deskripsi_product")
    private String deskripsi_product;

    @SerializedName("harga_product")
    private double harga_product;

    @SerializedName("gambar")
    private String gambar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_product() {
        return nama_product;
    }

    public void setNama_product(String nama_product) {
        this.nama_product = nama_product;
    }

    public String getDeskripsi_product() {
        return deskripsi_product;
    }

    public void setDeskripsi_product(String deskripsi_product) {
        this.deskripsi_product = deskripsi_product;
    }

    public double getHarga_product() {
        return harga_product;
    }

    public void setHarga_product(double harga_product) {
        this.harga_product = harga_product;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

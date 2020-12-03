package com.ryohandoko.restaurantuas.model;

import com.google.gson.annotations.SerializedName;

public class Pesanan {

    @SerializedName("id_pesanan")
    private String id_pesanan;

    @SerializedName("id_product")
    private String id_product;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("jumlah_pesan")
    private String jumlah_pesan;

    @SerializedName("nama_product")
    private String nama_product;

    @SerializedName("deskripsi_product")
    private String deskripsi_product;

    @SerializedName("harga_product")
    private String harga_product;

    @SerializedName("gambar_product")
    private String gambar_product;

    public String getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJumlah_pesan() {
        return jumlah_pesan;
    }

    public void setJumlah_pesan(String jumlah_pesan) {
        this.jumlah_pesan = jumlah_pesan;
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

    public String getHarga_product() {
        return harga_product;
    }

    public void setHarga_product(String harga_product) {
        this.harga_product = harga_product;
    }

    public String getGambar_product() {
        return gambar_product;
    }

    public void setGambar_product(String gambar_product) {
        this.gambar_product = gambar_product;
    }
}

package com.ryohandoko.restaurantuas.viewmodel;


import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.repository.ProductRepository;

public class DetailProductViewModel extends ViewModel {

    private ProductRepository repository;

    private ObservableField<String> id;
    private ObservableField<String> nama;
    private ObservableField<String> deskripsi;
    private ObservableField<String> harga;
    private ObservableField<String> url;

    public DetailProductViewModel() {
        super();
        repository = new ProductRepository();
        id = new ObservableField<>("");
        nama = new ObservableField<>("");
        deskripsi = new ObservableField<>("");
        harga = new ObservableField<>("");
        url = new ObservableField<>("");
    }

    public void retrieveProduct(String id) {
        repository.getProductById(id);
    }

    public void removeProduct(String id) {
        repository.hapusProduct(id);
    }

    public ObservableField<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public ObservableField<String> getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public ObservableField<String> getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi.set(deskripsi);
    }

    public ObservableField<String> getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga.set(harga);
    }

    public ObservableField<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public LiveData<String> getErrorMessage() {return repository.getErrorMessage();}

    public LiveData<Item> getItemLiveData() {return repository.getProductLiveData();}
}

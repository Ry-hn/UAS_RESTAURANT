package com.ryohandoko.restaurantuas.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.repository.PesananRepository;
import com.ryohandoko.restaurantuas.repository.ProductRepository;


public class DetailProductUserViewModel extends ViewModel {

    private ProductRepository repository;
    private PesananRepository pesananRepository;

    private ObservableField<String> namaProduct;
    private ObservableField<String> deskripsi;
    private ObservableField<String> harga;
    private ObservableField<String> gambar;
    private ObservableField<String> jumlahBeli;

    public DetailProductUserViewModel() {

        repository = new ProductRepository();
        pesananRepository = new PesananRepository();

        namaProduct = new ObservableField<>("");
        deskripsi = new ObservableField<>("");
        harga = new ObservableField<>("");
        gambar = new ObservableField<>("");
        jumlahBeli = new ObservableField<>("");
    }

    public void tambah(String iduser, String idProduct) {
        pesananRepository.tambah(iduser, idProduct, jumlahBeli.get());
    }

    public void retrieveProduct(String id) {
        repository.getProductById(id);
    }


    public ObservableField<String> getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct.set(namaProduct);
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

    public ObservableField<String> getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar.set(gambar);
    }

    public ObservableField<String> getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(String jumlahBeli) {
        this.jumlahBeli.set(jumlahBeli);
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

    public LiveData<String> getPesananMessage() {
        return pesananRepository.getErrorMessage();
    }

    public LiveData<Item> getItem() {
        return repository.getProductLiveData();
    }

}

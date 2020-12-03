package com.ryohandoko.restaurantuas.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.repository.PesananRepository;

public class DetailPesanViewModel extends ViewModel {

    private ObservableField<String> nama;
    private ObservableField<String> gambar;
    private ObservableField<String> jumlah;

    private PesananRepository repository;

    public DetailPesanViewModel() {
        repository = new PesananRepository();
        nama = new ObservableField<>("");
        gambar = new ObservableField<>("");
        jumlah = new ObservableField<>("");
    }

    public void hapus(String idPesanan) {
        repository.deletePesanan(idPesanan);
    }

    public void updatePesanan(String idPesanan) {
        repository.updatePesanan(idPesanan, jumlah.get());
    }

    public ObservableField<String> getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public ObservableField<String> getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar.set(gambar);
    }

    public ObservableField<String> getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah.set(jumlah);
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }
}

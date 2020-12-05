package com.ryohandoko.restaurantuas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.model.Pesanan;
import com.ryohandoko.restaurantuas.repository.PesananRepository;

import java.util.List;

public class PesananFragmentViewModel  extends ViewModel {

    private PesananRepository repository;

    public PesananFragmentViewModel() {
        repository = new PesananRepository();
    }

    public void getAllPesananUser(String idUser) {
        repository.getPesananUser(idUser);
    }

    public LiveData<List<Pesanan>> getListPesanan() {
        return repository.getListPesananLiveData();
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

}

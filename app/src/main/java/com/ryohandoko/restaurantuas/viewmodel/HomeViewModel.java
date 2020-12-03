package com.ryohandoko.restaurantuas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private ProductRepository repository;

    public HomeViewModel() {

        repository = new ProductRepository();
    }

    public void loadData() {
        repository.getProducts();
    }


    public LiveData<List<Item>> getProductsLiveData() {return repository.getListProductsLiveData(); }
    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

}

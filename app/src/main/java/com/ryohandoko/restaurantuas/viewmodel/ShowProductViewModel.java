package com.ryohandoko.restaurantuas.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.repository.ProductRepository;

import java.util.List;

public class ShowProductViewModel extends ViewModel {
    private ProductRepository repository;

    private ObservableField<String> searchField = new ObservableField<>("");
    private ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public ShowProductViewModel() {
        repository = new ProductRepository();
    }

    public void getProducts() {
        isLoading.set(true);
        repository.getProducts();
    }

    public LiveData<List<Item>> getProductsLiveData() {return repository.getListProductsLiveData(); }
    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

    public ObservableField<String> getSearchField() { return searchField; }

    public void setSearchField(String searchField) { this.searchField.set(searchField); }

    public ObservableField<Boolean> getIsLoading() { return isLoading; }

    public void setIsLoading(Boolean isLoading) { this.isLoading.set(isLoading); }
}

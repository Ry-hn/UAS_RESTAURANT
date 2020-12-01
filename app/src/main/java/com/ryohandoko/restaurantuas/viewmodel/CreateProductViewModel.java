package com.ryohandoko.restaurantuas.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ryohandoko.restaurantuas.repository.ProductRepository;

public class CreateProductViewModel extends ViewModel {

    private ProductRepository repository;

    private ObservableField<String> namaProduct = new ObservableField<>("");
    private ObservableField<String> deskripsiProduct = new ObservableField<>("");
    private ObservableField<String> hargaProduct = new ObservableField<>("");
    private ObservableField<String> urlGambar = new ObservableField<>("");

    private ObservableField<Boolean> isLoading = new ObservableField<>(false);

    public CreateProductViewModel() {
        super();
        repository = new ProductRepository();
    }

    public void create() {
        isLoading.set(true);
        repository.createProduct(namaProduct.get(), deskripsiProduct.get(),
                                    Double.parseDouble(hargaProduct.get()), urlGambar.get());
    }

    public ObservableField<String> getNamaProduct() { return namaProduct; }
    public void setNamaProduct(String namaProduct) { this.namaProduct.set(namaProduct); }

    public ObservableField<String> getDeskripsiProduct() { return deskripsiProduct; }
    public void setDeskripsiProduct(String deskripsiProduct) { this.deskripsiProduct.set(deskripsiProduct); }

    public ObservableField<String> getHargaProduct() { return hargaProduct; }
    public void setHargaProduct(String hargaProduct) { this.hargaProduct.set(hargaProduct); }

    public ObservableField<String> getUrlGambar() { return urlGambar; }
    public void setUrlGambar(String urlGambar) { this.urlGambar.set(urlGambar); }

    public ObservableField<Boolean> getIsLoading() { return isLoading; }
    public void setIsLoading(boolean isLoading) { this.isLoading.set(isLoading); }

    public LiveData<String> getErrorMessage() { return repository.getErrorMessage(); }

}

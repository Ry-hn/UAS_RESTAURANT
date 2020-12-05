package com.ryohandoko.restaurantuas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CreateProductTest {

    private ProductRepository repository;

    @Before
    public void init() {
        repository = Mockito.mock(ProductRepository.class);
    }

    @Test
    public void ShouldReturnAddProductSuccess() {
        when(repository.createProduct(anyString(), anyString(), anyString(), anyString())).thenReturn(new MutableLiveData<>("Add Product Success"));

        LiveData<String> res = repository.createProduct("durian", "durian", "10000", "url");

        assertEquals(res.getValue(), "Add Product Success");
    }

    @Test
    public void ShouldReturnAddProductErrorDuplicatedProductName() {
        when(repository.createProduct(anyString(), anyString(), anyString(), anyString())).thenReturn(new MutableLiveData<>("Duplicated Product Name"));

        LiveData<String> res = repository.createProduct("durian", "durian", "10000", "url");

        assertEquals(res.getValue(), "Add Product Success");
    }

    @Test
    public void ShouldReturnAddProductErrorEmptyProductName() {
        when(repository.createProduct(anyString(), anyString(), anyString(), anyString())).thenReturn(new MutableLiveData<>("Empty Product Name"));

        LiveData<String> res = repository.createProduct("", "durian", "10000", "url");

        assertEquals(res.getValue(), "Empty Product Name");
    }

}

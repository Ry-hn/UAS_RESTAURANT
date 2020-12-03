package com.ryohandoko.restaurantuas.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiPesananInterface;
import com.ryohandoko.restaurantuas.API.Response.PesananResponse;
import com.ryohandoko.restaurantuas.model.Pesanan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananRepository {

    private final ApiPesananInterface apiService;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<List<Pesanan>> listPesananLiveData;

    public PesananRepository() {
        apiService = ApiClient.getClient().create(ApiPesananInterface.class);
        listPesananLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public void tambah(String idUser, String idProduct, String jumlah) {
        Call<PesananResponse> request = apiService.tambahPesanan(idUser, idProduct, jumlah);

        request.enqueue(new Callback<PesananResponse>() {
            @Override
            public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                if(response.code() == 200) {
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<PesananResponse> call, Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("500");
            }
        });
    }

    public void getPesananUser(String idUser) {
        Call<PesananResponse> request = apiService.getPesananUser(idUser);

        request.enqueue(new Callback<PesananResponse>() {
            @Override
            public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                if(response.code() == 200) {
                    listPesananLiveData.postValue(response.body().getListPesanan());

//                    Log.i("TELOLETOM", "onResponse: " + listPesananLiveData.getValue().get(0).toString());

                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<PesananResponse> call, Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("500");
            }
        });

    }

    public void deletePesanan(String idPesanan) {
        Call<PesananResponse> request = apiService.deletePesanan(idPesanan);

        request.enqueue(new Callback<PesananResponse>() {
            @Override
            public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                if(response.code() == 200) {
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<PesananResponse> call, Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("500");
            }
        });
    }

    public void updatePesanan(String idPesanan, String jml) {
        Call<PesananResponse> request = apiService.updatePesanan(idPesanan, jml);

        request.enqueue(new Callback<PesananResponse>() {
            @Override
            public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                if(response.code() == 200) {
                    errorMessage.postValue(response.body().getMessage());
                }
                else errorMessage.postValue("400");
            }

            @Override
            public void onFailure(Call<PesananResponse> call, Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("500");
            }
        });
    }


    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<Pesanan>> getListPesananLiveData() {
        return listPesananLiveData;
    }
}

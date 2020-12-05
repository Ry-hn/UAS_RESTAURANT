package com.ryohandoko.restaurantuas.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentDetailProductUserBinding;
import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.viewmodel.DetailProductUserViewModel;

public class DetailProductUser extends DialogFragment {

    private FragmentDetailProductUserBinding binding;
    private DetailProductUserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this).get(DetailProductUserViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product_user, container, false);
        binding.setLifecycleOwner(this);
        binding.setVM(viewModel);
        binding.setView(this);

        viewModel.retrieveProduct(getArguments().getString("id", ""));

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "Retrieve Product Success":
                        loadData();
                        break;
                    case "Add Product Success":
                        dismiss();
                        break;
                    default:
                        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        viewModel.getPesananMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "Tambah Jumlah Pesan Berhasil":
                    case "Add Pesanan Success":
                        Toast.makeText(getContext(), "Pesanan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        dismiss();
                        break;
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        this.viewModel = null;
    }

    public void Tambah(View view) {
        SharedPreferences sp = getActivity().getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        String idUser = sp.getString("id", "");
        String idProduct = getArguments().getString("id", "");

        viewModel.tambah(idUser, idProduct);
    }

    private void loadData() {
        Item item = viewModel.getItem().getValue();

        viewModel.setNamaProduct(item.getNama_product());
        viewModel.setDeskripsi(item.getDeskripsi_product());
        viewModel.setHarga(item.getHarga_product());
        viewModel.setGambar(item.getGambar());
    }
}
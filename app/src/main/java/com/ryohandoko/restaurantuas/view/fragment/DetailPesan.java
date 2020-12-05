package com.ryohandoko.restaurantuas.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentDetailPesanBinding;
import com.ryohandoko.restaurantuas.viewmodel.DetailPesanViewModel;

public class DetailPesan extends DialogFragment {

    private FragmentDetailPesanBinding binding;
    private DetailPesanViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(DetailPesanViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_pesan, container, false);
        binding.setView(this);
        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);

        load();

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.i("TELOLETSUPER", "onChanged: string " + s);

                switch (s) {
                    case "Update Pesanan Success":
                    case "Delete Pesanan Success":
                        dismiss();
                        Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        binding.executePendingBindings();

        return binding.getRoot();
    }

    public void load() {
        String nama = getArguments().getString("nama", "");
        String gambar = getArguments().getString("gambar", "");
        String jumlah = getArguments().getString("jumlah", "");

        viewModel.setGambar(gambar);
        viewModel.setNama(nama);
        viewModel.setJumlah(jumlah);
    }

    public void Hapus(View view) {
        String idPesanan = getArguments().getString("idPesanan", "");

        Log.i("DETAIL", "Hapus: " + idPesanan);

        viewModel.hapus(idPesanan);
    }

    public void Update(View view) {
        String idPesanan = getArguments().getString("idPesanan", "");

        Log.i("DETAIL", "Hapus: " + idPesanan);

        viewModel.updatePesanan(idPesanan);
    }

}
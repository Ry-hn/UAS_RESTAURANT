package com.ryohandoko.restaurantuas.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryohandoko.restaurantuas.databinding.AdapterPesananBinding;
import com.ryohandoko.restaurantuas.model.Pesanan;
import com.ryohandoko.restaurantuas.view.fragment.DetailPesan;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.PesananViewHolder> {


    private AdapterPesananBinding binding;
    private List<Pesanan> listPesanan;
    private Context context;

    public PesananAdapter(List<Pesanan> listPesanan, Context context) {
        this.listPesanan = listPesanan;
        this.context = context;
    }

    @NonNull
    @Override
    public PesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = AdapterPesananBinding.inflate(layoutInflater, parent, false);

        return new PesananViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananViewHolder holder, int position) {
        Pesanan pesanan = listPesanan.get(position);

        Log.i("TELOLET", "onBindViewHolder: id pesanan" + pesanan.getId_pesanan());

        holder.bind(pesanan);

        holder.itemView.setOnClickListener( v -> {
            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            DetailPesan dialog = new DetailPesan();
            dialog.show(manager, "dialog");

            Bundle args = new Bundle();
            args.putString("idPesanan", pesanan.getId_pesanan());
            args.putString("nama", pesanan.getNama_product());
            args.putString("gambar", pesanan.getGambar_product());
            args.putString("jumlah", pesanan.getJumlah_pesan());
            dialog.setArguments(args);
        });
    }

    @Override
    public int getItemCount() {
        return listPesanan.size();
    }

    static class PesananViewHolder extends RecyclerView.ViewHolder {
        AdapterPesananBinding binding;

        public PesananViewHolder(AdapterPesananBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Pesanan pesanan) {
            this.binding.setPesanan(pesanan);
            this.binding.executePendingBindings();
        }
    }

}

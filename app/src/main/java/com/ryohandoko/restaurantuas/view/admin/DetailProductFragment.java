package com.ryohandoko.restaurantuas.view.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentDetailProductBinding;
import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.viewmodel.DetailProductViewModel;

public class DetailProductFragment extends DialogFragment {

    private FragmentDetailProductBinding binding;
    private DetailProductViewModel viewModel;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(DetailProductViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product, container, false);
        binding.setLifecycleOwner(this);
        binding.setVM(viewModel);
        binding.setView(this);

        getProduct();

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.i("DETAILPRODUCT", "onChanged: String" + s);

                switch (s) {
                    case "Retrieve Product Success":
                        loadData();
                        break;
                    case "Update Product Success":
                    case "Delete Product Success":
                        dismiss();
                        break;
                    default:
                        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.executePendingBindings();

        return binding.getRoot();
    }

    public void BtnSave(View view) {
        viewModel.saveProduct();
    }

    public void BtnHapus(View view) {
        String id = getArguments().getString("id", "");

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        viewModel.removeProduct(id);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Hapus data ini ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void getProduct() {
        String id = getArguments().getString("id", "");
        viewModel.retrieveProduct(id);
    }

    private void loadData() {
        Item item = viewModel.getItemLiveData().getValue();

        viewModel.setId(item.getId());
        viewModel.setNama(item.getNama_product());
        viewModel.setDeskripsi(item.getDeskripsi_product());
        viewModel.setHarga(item.getHarga_product());
        viewModel.setUrl(item.getGambar());
    }

}
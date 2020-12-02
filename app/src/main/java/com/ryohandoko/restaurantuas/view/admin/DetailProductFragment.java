package com.ryohandoko.restaurantuas.view.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentDetailProductBinding;
import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.viewmodel.DetailProductViewModel;
import com.ryohandoko.restaurantuas.viewmodel.ProfileViewModel;

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

        getProduct();

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.i("DETAILPRODUCT", "onChanged: String" + s);

                switch (s) {
                    case "Retrieve Product Success":
                        loadData();
                        break;
                }
            }
        });

        binding.executePendingBindings();

        return binding.getRoot();
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
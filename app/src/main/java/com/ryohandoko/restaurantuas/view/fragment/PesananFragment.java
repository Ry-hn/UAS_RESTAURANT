package com.ryohandoko.restaurantuas.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.Adapter.PesananAdapter;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentPesananBinding;
import com.ryohandoko.restaurantuas.viewmodel.PesananFragmentViewModel;

public class PesananFragment extends Fragment {

    private FragmentPesananBinding binding;
    private PesananFragmentViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PesananAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PesananFragmentViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pesanan, container, false);

        binding.setView(this);
        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);

        swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

        swipeRefreshLayout.setOnRefreshListener( () -> {
            getData();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                swipeRefreshLayout.setRefreshing(false);

                switch (s) {
                    case "Retrieve Pesanan Success":

                        adapter  = new PesananAdapter(viewModel.getListPesanan().getValue(), getContext());
                        recyclerView.setAdapter(adapter);
                        break;
                    default:
                        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.executePendingBindings();

        return  binding.getRoot();
    }

    private void getData() {
        SharedPreferences sp = getActivity().getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        String id = sp.getString("id", "");

        viewModel.getAllPesananUser(id);
    }

    public void back(View view) {
        getActivity().onBackPressed();
    }

}
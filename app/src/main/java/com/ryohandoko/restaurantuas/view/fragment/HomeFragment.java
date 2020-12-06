package com.ryohandoko.restaurantuas.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ryohandoko.restaurantuas.Adapter.ItemUserAdapter;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentHomeBinding;
import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private ItemUserAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private List<Item> listItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);


        recyclerView = binding.getRoot().findViewById(R.id.user_recyclerview_product);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        searchView = binding.getRoot().findViewById(R.id.searchProduct);
        swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setRefreshing(true);
        load();
        swipeRefreshLayout.setOnRefreshListener(this::load);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(adapter != null)
                    adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(adapter != null)
                    adapter.getFilter().filter(s);
                return false;
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                swipeRefreshLayout.setRefreshing(false);

                switch (s) {
                    case "Retrieve All Success":

                        listItem = viewModel.getProductsLiveData().getValue();

                        adapter = new ItemUserAdapter(getContext(), viewModel.getProductsLiveData().getValue());
                        recyclerView.setAdapter(adapter);
                        break;
                }
            }
        });

        binding.executePendingBindings();

        return binding.getRoot();
    }


    private void load() {
        viewModel.loadData();
    }
}